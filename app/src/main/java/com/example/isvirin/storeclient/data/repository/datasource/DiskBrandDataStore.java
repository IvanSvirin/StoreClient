package com.example.isvirin.storeclient.data.repository.datasource;

import com.example.isvirin.storeclient.data.cache.BrandCache;
import com.example.isvirin.storeclient.data.entity.BrandEntity;

import java.util.List;

import rx.Observable;

public class DiskBrandDataStore implements BrandDataStore{
    private final BrandCache brandCache;

    public DiskBrandDataStore(BrandCache brandCache) {
        this.brandCache = brandCache;
    }

    @Override
    public Observable<List<BrandEntity>> brandEntityList() {
        return this.brandCache.getBrands();
    }

    @Override
    public Observable<List<BrandEntity>> brandEntitiesByCategory(int id) {
        return this.brandCache.getBrandsByCategoryId(id);
    }
}
