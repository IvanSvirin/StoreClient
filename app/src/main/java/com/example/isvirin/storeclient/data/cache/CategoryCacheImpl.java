package com.example.isvirin.storeclient.data.cache;

import android.content.Context;

import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.CategoryEntityDao;
import com.example.isvirin.storeclient.data.exception.CategoryNotFoundException;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link CategoryCache} implementation.
 */
@Singleton
public class CategoryCacheImpl implements CategoryCache {
    private static final String SETTINGS_FILE_NAME = "category_settings";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "category_last_cache_update";

    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final FileManager fileManager;
    private final GetDaoSession getDaoSession;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link CategoryCacheImpl}.
     *
     * @param context
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public CategoryCacheImpl(Context context, GetDaoSession getDaoSession, FileManager fileManager, ThreadExecutor executor) {
        if (context == null || fileManager == null || getDaoSession == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.fileManager = fileManager;
        this.getDaoSession = getDaoSession;
        this.threadExecutor = executor;
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
    public Observable<List<CategoryEntity>> getCategories() {
        return Observable.create(subscriber -> {
            CategoryEntityDao categoryEntityDao = getDaoSession.getDaoSession().getCategoryEntityDao();
            Query<CategoryEntity> query = categoryEntityDao.queryBuilder().orderAsc(CategoryEntityDao.Properties.Name).build();
            List<CategoryEntity> categoryEntities = query.list();
            if (categoryEntities != null) {
                subscriber.onNext(categoryEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new CategoryNotFoundException());
            }
        });
    }

    @Override
    public void putCategories(List<CategoryEntity> categoryEntities) {
        if (categoryEntities != null) {
            if (!isCategoriesCached()) {
                this.executeAsynchronously(new CacheWriter(getDaoSession, categoryEntities));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCategoriesCached() {
        if (getDaoSession.getDaoSession().getCategoryEntityDao().count() > 0) {
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
        private final List<CategoryEntity> categoryEntities;

        CacheWriter(GetDaoSession getDaoSession, List<CategoryEntity> categoryEntities) {
            this.getDaoSession = getDaoSession;
            this.categoryEntities = categoryEntities;
        }

        @Override
        public void run() {
            CategoryEntityDao categoryEntityDao = getDaoSession.getDaoSession().getCategoryEntityDao();
            for (CategoryEntity categoryEntity : categoryEntities) {
                categoryEntityDao.insert(categoryEntity);
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
