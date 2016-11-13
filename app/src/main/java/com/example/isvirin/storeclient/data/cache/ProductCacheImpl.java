package com.example.isvirin.storeclient.data.cache;

import android.content.Context;

import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntityDao;
import com.example.isvirin.storeclient.data.exception.ProductNotFoundException;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link ProductCache} implementation.
 */
@Singleton
public class ProductCacheImpl implements ProductCache {
    private static final String SETTINGS_FILE_NAME = "product_settings";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "product_last_cache_update";

    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final FileManager fileManager;
    private final GetDaoSession getDaoSession;
    private final ThreadExecutor threadExecutor;

    @Inject
    public ProductCacheImpl(Context context, GetDaoSession getDaoSession, FileManager fileManager, ThreadExecutor executor) {
        if (context == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.getDaoSession = getDaoSession;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<ProductEntity> get(int id) {
        return Observable.create(subscriber -> {
            ProductEntity productEntity = new ProductEntity();
            ProductEntityDao productEntityDao = getDaoSession.getDaoSession().getProductEntityDao();
            Query<ProductEntity> query = productEntityDao.queryBuilder().where(ProductEntityDao.Properties.ProductId.eq(id)).build();
            List<ProductEntity> productEntities = query.list();
            productEntity = productEntities.get(0);
            if (productEntity != null) {
                subscriber.onNext(productEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ProductNotFoundException());
            }
        });
    }

    @Override
    public boolean isCached(int id) {
        if (getDaoSession.getDaoSession().getProductEntityDao().count() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();
        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);
        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor());
    }

    @Override
    public Observable<List<ProductEntity>> getProductsByBrand(String brandName) {
        return Observable.create(subscriber -> {
            ProductEntityDao productEntityDao = getDaoSession.getDaoSession().getProductEntityDao();
            Query<ProductEntity> query = productEntityDao.queryBuilder().where(ProductEntityDao.Properties.Brand.eq(brandName)).build();
            List<ProductEntity> productEntities = query.list();
            if (productEntities != null) {
                subscriber.onNext(productEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ProductNotFoundException());
            }
        });
    }

    @Override
    public Observable<List<ProductEntity>> getProducts() {
        return Observable.create(subscriber -> {
            ProductEntityDao productEntityDao = getDaoSession.getDaoSession().getProductEntityDao();
            Query<ProductEntity> query = productEntityDao.queryBuilder().orderAsc(ProductEntityDao.Properties.ProductId).build();
            List<ProductEntity> productEntities = query.list();
            if (productEntities != null) {
                subscriber.onNext(productEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ProductNotFoundException());
            }
        });
    }

    @Override
    public void putProducts(List<ProductEntity> productEntities) {
        if (productEntities != null) {
            if (!isProductsCached()) {
                this.executeAsynchronously(new CacheWriter(getDaoSession, productEntities));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isProductsCached() {
        if (getDaoSession.getDaoSession().getProductEntityDao().count() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final GetDaoSession getDaoSession;
        private final List<ProductEntity> productEntities;

        CacheWriter(GetDaoSession getDaoSession, List<ProductEntity> productEntities) {
            this.getDaoSession = getDaoSession;
            this.productEntities = productEntities;
        }

        @Override
        public void run() {
            ProductEntityDao productEntityDao = getDaoSession.getDaoSession().getProductEntityDao();
            for (ProductEntity productEntity : productEntities) {
                productEntityDao.insert(productEntity);
            }
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {

        CacheEvictor() {
        }

        @Override
        public void run() {
        }
    }
}
