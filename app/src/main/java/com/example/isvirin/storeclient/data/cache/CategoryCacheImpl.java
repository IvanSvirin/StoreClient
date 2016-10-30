package com.example.isvirin.storeclient.data.cache;

import android.content.Context;

import com.example.isvirin.storeclient.data.cache.serializer.JsonSerializer;
import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.CategoryEntityDao;
import com.example.isvirin.storeclient.data.exception.CategoryNotFoundException;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;

import org.greenrobot.greendao.query.Query;

import java.io.File;
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

    private static final String DEFAULT_FILE_NAME = "category_";
    private static final String DEFAULT_PRODUCTS_FILE_NAME = "categories";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final GetDaoSession getDaoSession;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link CategoryCacheImpl}.
     *
     * @param context                 A
     * @param categoryCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager             {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public CategoryCacheImpl(Context context, JsonSerializer categoryCacheSerializer, GetDaoSession getDaoSession,
                             FileManager fileManager, ThreadExecutor executor) {
        if (context == null || categoryCacheSerializer == null || fileManager == null || getDaoSession == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = categoryCacheSerializer;
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
        this.executeAsynchronously(new CategoryCacheImpl.CacheEvictor(this.fileManager, this.cacheDir));
    }

    @Override
    public Observable<List<CategoryEntity>> getCategories() {
        return Observable.create(subscriber -> {
//            File categoryEntitiesFile = CategoryCacheImpl.this.buildFile(-1);
//            String fileContent = CategoryCacheImpl.this.fileManager.readFileContent(categoryEntitiesFile);
//            List<CategoryEntity> categoryEntities = CategoryCacheImpl.this.serializer.deserializeCategories(fileContent);

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
            File categoryEntitiesFile = this.buildFile(-1);
            if (!isCategoriesCached()) {
//                String jsonString = this.serializer.serializeCategories(categoryEntities);
//                this.executeAsynchronously(new CategoryCacheImpl.CacheWriter(this.fileManager, categoryEntitiesFile, jsonString));
                CategoryEntityDao categoryEntityDao = getDaoSession.getDaoSession().getCategoryEntityDao();
                for (CategoryEntity categoryEntity : categoryEntities) {
                    categoryEntityDao.insert(categoryEntity);
                }
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCategoriesCached() {
        File productEntitiesFile = this.buildFile(-1);
        return this.fileManager.exists(productEntitiesFile);
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param id The product id to build the file.
     * @return A valid file.
     */
    private File buildFile(int id) {
        if (id != -1) {
            StringBuilder fileNameBuilder = new StringBuilder();
            fileNameBuilder.append(this.cacheDir.getPath());
            fileNameBuilder.append(File.separator);
            fileNameBuilder.append(DEFAULT_FILE_NAME);
            fileNameBuilder.append(id);

            return new File(fileNameBuilder.toString());
        } else {
            StringBuilder fileNameBuilder = new StringBuilder();
            fileNameBuilder.append(this.cacheDir.getPath());
            fileNameBuilder.append(File.separator);
            fileNameBuilder.append(DEFAULT_FILE_NAME);

            return new File(fileNameBuilder.toString());
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
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
