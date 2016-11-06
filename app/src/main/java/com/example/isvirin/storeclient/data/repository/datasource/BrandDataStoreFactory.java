/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.isvirin.storeclient.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.isvirin.storeclient.data.cache.BrandCache;
import com.example.isvirin.storeclient.data.entity.mapper.EntityJsonMapper;
import com.example.isvirin.storeclient.data.net.RestApi;
import com.example.isvirin.storeclient.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrandDataStoreFactory {
    private final Context context;
    private final BrandCache brandCache;

    @Inject
    public BrandDataStoreFactory(@NonNull Context context, @NonNull BrandCache brandCache) {
        this.context = context.getApplicationContext();
        this.brandCache = brandCache;
    }

    public BrandDataStore createListByCategoryId(int id) {
        BrandDataStore brandDataStore;
        if (!this.brandCache.isExpired() && this.brandCache.isBrandsCached()) {
            brandDataStore = new DiskBrandDataStore(this.brandCache);
        } else {
            brandDataStore = createCloudDataStore();
        }
        return brandDataStore;
    }

    public BrandDataStore createList() {
        BrandDataStore brandDataStore;
        if (!this.brandCache.isExpired() && this.brandCache.isBrandsCached()) {
            brandDataStore = new DiskBrandDataStore(this.brandCache);
        } else {
            brandDataStore = createCloudDataStore();
        }
        return brandDataStore;
    }

    public BrandDataStore createCloudDataStore() {
        EntityJsonMapper entityJsonMapper = new EntityJsonMapper();
        RestApi restApi = new RestApiImpl(this.context, entityJsonMapper);
        return new CloudBrandDataStore(restApi, this.brandCache);
    }
}
