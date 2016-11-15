package com.example.isvirin.storeclient.data.cache;

import android.content.Context;

import com.example.isvirin.storeclient.data.entity.ChosenItemEntity;
import com.example.isvirin.storeclient.data.entity.ChosenItemEntityDao;
import com.example.isvirin.storeclient.data.exception.ChosenItemNotFoundException;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class ChosenItemCacheImpl implements ChosenItemCache {
    private final Context context;
    private final FileManager fileManager;
    private final GetDaoSession getDaoSession;
    private final ThreadExecutor threadExecutor;

    @Inject
    public ChosenItemCacheImpl(Context context, FileManager fileManager, GetDaoSession getDaoSession, ThreadExecutor threadExecutor) {
        if (context == null || fileManager == null || getDaoSession == null || threadExecutor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context;
        this.fileManager = fileManager;
        this.getDaoSession = getDaoSession;
        this.threadExecutor = threadExecutor;
    }

    @Override
    public void addItem(ChosenItemEntity chosenItemEntity) {
        if (chosenItemEntity != null) {
            this.executeAsynchronously(new CacheWriter(getDaoSession, chosenItemEntity));
        }
    }

    @Override
    public void removeItem(ChosenItemEntity chosenItemEntity) {
        Observable.create(subscriber -> {
            ChosenItemEntityDao chosenItemEntityDao = getDaoSession.getDaoSession().getChosenItemEntityDao();
            DeleteQuery<ChosenItemEntity> query = chosenItemEntityDao.queryBuilder()
                    .where(ChosenItemEntityDao.Properties.Id.eq(chosenItemEntity.getId())).buildDelete();
            query.executeDeleteWithoutDetachingEntities();
        });
    }

    @Override
    public Observable<List<ChosenItemEntity>> getItems() {
        return Observable.create(subscriber -> {
            ChosenItemEntityDao chosenItemEntityDao = getDaoSession.getDaoSession().getChosenItemEntityDao();
            Query<ChosenItemEntity> query = chosenItemEntityDao.queryBuilder().orderAsc(ChosenItemEntityDao.Properties.Name).build();
            List<ChosenItemEntity> chosenItemEntities = query.list();
            if (chosenItemEntities != null) {
                subscriber.onNext(chosenItemEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ChosenItemNotFoundException());
            }
        });
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new ChosenItemCacheImpl.CacheEvictor());
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    private static class CacheWriter implements Runnable {
        private final GetDaoSession getDaoSession;
        private final ChosenItemEntity chosenItemEntity;

        CacheWriter(GetDaoSession getDaoSession, ChosenItemEntity chosenItemEntity) {
            this.getDaoSession = getDaoSession;
            this.chosenItemEntity = chosenItemEntity;
        }

        @Override
        public void run() {
            ChosenItemEntityDao chosenItemEntityDao = getDaoSession.getDaoSession().getChosenItemEntityDao();
            chosenItemEntityDao.insert(chosenItemEntity);
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
