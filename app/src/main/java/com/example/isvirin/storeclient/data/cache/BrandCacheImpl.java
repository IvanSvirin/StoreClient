package com.example.isvirin.storeclient.data.cache;

import android.content.Context;

import com.example.isvirin.storeclient.data.entity.BrandEntity;
import com.example.isvirin.storeclient.data.entity.BrandEntityDao;
import com.example.isvirin.storeclient.data.exception.BrandNotFoundException;
import com.example.isvirin.storeclient.data.exception.ProductNotFoundException;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class BrandCacheImpl implements BrandCache {
    private static final String SETTINGS_FILE_NAME = "brand_settings";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "brand_last_cache_update";

    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final FileManager fileManager;
    private final GetDaoSession getDaoSession;
    private final ThreadExecutor threadExecutor;

    @Inject
    public BrandCacheImpl(Context context, FileManager fileManager, GetDaoSession getDaoSession, ThreadExecutor threadExecutor) {
        if (context == null || fileManager == null || getDaoSession == null || threadExecutor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context;
        this.fileManager = fileManager;
        this.getDaoSession = getDaoSession;
        this.threadExecutor = threadExecutor;
    }

    @Override
    public Observable<List<BrandEntity>> getBrandsByCategoryId(int id) {
        return Observable.create(subscriber -> {
            BrandEntityDao brandEntityDao = getDaoSession.getDaoSession().getBrandEntityDao();
            Query<BrandEntity> query = brandEntityDao.queryBuilder().where(BrandEntityDao.Properties.CategoryId.eq(id)).build();
            List<BrandEntity> brandEntities = query.list();
            if (brandEntities != null) {
                subscriber.onNext(brandEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ProductNotFoundException());
            }
        });
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
    public Observable<List<BrandEntity>> getBrands() {
        return Observable.create(subscriber -> {
            BrandEntityDao brandEntityDao = getDaoSession.getDaoSession().getBrandEntityDao();
            Query<BrandEntity> query = brandEntityDao.queryBuilder().orderAsc(BrandEntityDao.Properties.Name).build();
            List<BrandEntity> brandEntities = query.list();
            if (brandEntities != null) {
                subscriber.onNext(brandEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new BrandNotFoundException());
            }
        });
    }

    @Override
    public void putBrands(List<BrandEntity> brandEntities) {
        if (brandEntities != null) {
            if (!isBrandsCached()) {
                this.executeAsynchronously(new CacheWriter(getDaoSession, brandEntities));
                setLastCacheUpdateTimeMillis();
            }
        }

    }

    @Override
    public boolean isBrandsCached() {
        if (getDaoSession.getDaoSession().getBrandEntityDao().count() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    private static class CacheWriter implements Runnable {
        private final GetDaoSession getDaoSession;
        private final List<BrandEntity> brandEntities;

        CacheWriter(GetDaoSession getDaoSession, List<BrandEntity> brandEntities) {
            this.getDaoSession = getDaoSession;
            this.brandEntities = brandEntities;
        }

        @Override
        public void run() {
            BrandEntityDao brandEntityDao = getDaoSession.getDaoSession().getBrandEntityDao();
            for (BrandEntity brandEntity : brandEntities) {
                brandEntityDao.insert(brandEntity);
            }
        }
    }


    private static class CacheEvictor implements Runnable {

        CacheEvictor() {
        }

        @Override
        public void run() {
        }
    }
}
