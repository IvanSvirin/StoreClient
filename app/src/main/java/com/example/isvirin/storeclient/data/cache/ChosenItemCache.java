package com.example.isvirin.storeclient.data.cache;

import com.example.isvirin.storeclient.data.entity.ChosenItemEntity;

import java.util.List;

import rx.Observable;

public interface ChosenItemCache {

    void addItem(ChosenItemEntity chosenItemEntity);

    void removeItem(ChosenItemEntity chosenItemEntity);

    Observable<List<ChosenItemEntity>> getItems();

    void evictAll();
}
