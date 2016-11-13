/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.isvirin.storeclient.data.repository.datasource;


import com.example.isvirin.storeclient.data.cache.ProductCache;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.net.RestApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link ProductDataStore} implementation based on connections to the api (Cloud).
 */
class CloudProductDataStore implements ProductDataStore {

  private final RestApi restApi;
  private final ProductCache productCache;

  private final Action1<List<ProductEntity>> saveListToCacheAction = productEntities -> {
    if (productEntities != null) {
      CloudProductDataStore.this.productCache.putProducts(productEntities);
    }
  };

  /**
   * Construct a {@link ProductDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param productCache A {@link ProductCache} to cache data retrieved from the api.
   */
  CloudProductDataStore(RestApi restApi, ProductCache productCache) {
    this.restApi = restApi;
    this.productCache = productCache;
  }

  @Override
  public Observable<List<ProductEntity>> productEntityList() {
    return this.restApi.productEntityList().doOnNext(saveListToCacheAction);
  }

  @Override
  public Observable<List<ProductEntity>> productEntitiesByBrand(String brandName) {
    return null;
  }

  @Override
  public Observable<ProductEntity> productEntityDetails(int id) {
    return null;
//    return this.restApi.productEntityById(id).doOnNext(saveToCacheAction);
  }
}
