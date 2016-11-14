package com.example.isvirin.storeclient.data.cache;

import com.example.isvirin.storeclient.data.entity.ChosenItemEntity;

import java.util.List;

import rx.Observable;

public class ChosenItemCacheImpl implements ChosenItemCache {
    @Override
    public void addItem(ChosenItemEntity chosenItemEntity) {

    }

    @Override
    public void removeItem(ChosenItemEntity chosenItemEntity) {

    }

    @Override
    public Observable<List<ChosenItemEntity>> getItems() {
        return null;
    }

    @Override
    public void evictAll() {

    }
}
