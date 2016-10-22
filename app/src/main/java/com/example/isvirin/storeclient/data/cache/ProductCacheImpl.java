package com.example.isvirin.storeclient.data.cache;

import android.content.Context;

import com.example.isvirin.storeclient.data.cache.serializer.JsonSerializer;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.exception.ProductNotFoundException;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;

import java.io.File;
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

    private static final String DEFAULT_FILE_NAME = "product_";
    private static final String DEFAULT_PRODUCTS_FILE_NAME = "products";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link ProductCacheImpl}.
     *
     * @param context                A
     * @param productCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager            {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public ProductCacheImpl(Context context, JsonSerializer productCacheSerializer,
                            FileManager fileManager, ThreadExecutor executor) {
        if (context == null || productCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = productCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<ProductEntity> get(int id) {
        return Observable.create(subscriber -> {
            File userEntityFile = ProductCacheImpl.this.buildFile(id);
            String fileContent = ProductCacheImpl.this.fileManager.readFileContent(userEntityFile);
            ProductEntity productEntity = ProductCacheImpl.this.serializer.deserializeProduct(fileContent);
            if (productEntity != null) {
                subscriber.onNext(productEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ProductNotFoundException());
            }
        });
    }

    @Override
    public void put(ProductEntity productEntity) {
        if (productEntity != null) {
            File productEntityFile = this.buildFile(productEntity.getId());
            if (!isCached(productEntity.getId())) {
                String jsonString = this.serializer.serializeProduct(productEntity);
                this.executeAsynchronously(new ProductCacheImpl.CacheWriter(this.fileManager, productEntityFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int id) {
        File productEntityFile = this.buildFile(id);
        return this.fileManager.exists(productEntityFile);
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
        this.executeAsynchronously(new ProductCacheImpl.CacheEvictor(this.fileManager, this.cacheDir));
    }

    @Override
    public Observable<List<ProductEntity>> getProducts() {
        return Observable.create(subscriber -> {
            File productEntitiesFile = ProductCacheImpl.this.buildFile(-1);
            String fileContent = ProductCacheImpl.this.fileManager.readFileContent(productEntitiesFile);
            List<ProductEntity> productEntities = ProductCacheImpl.this.serializer.deserializeProducts(fileContent);
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
            File productEntitiesFile = this.buildFile(-1);
            if (!isProductsCached()) {
                String jsonString = this.serializer.serializeProducts(productEntities);
                this.executeAsynchronously(new ProductCacheImpl.CacheWriter(this.fileManager, productEntitiesFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isProductsCached() {
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
