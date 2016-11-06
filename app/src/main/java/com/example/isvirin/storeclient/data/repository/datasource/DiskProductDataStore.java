/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import java.util.List;

import rx.Observable;

/**
 * {@link ProductDataStore} implementation based on file system data store.
 */
class DiskProductDataStore implements ProductDataStore {
  private final ProductCache productCache;

  /**
   * Construct a {@link ProductDataStore} based file system data store.
   *
   * @param productCache A {@link ProductCache} to cache data retrieved from the api.
   */
  DiskProductDataStore(ProductCache productCache) {
    this.productCache = productCache;
  }

  @Override
  public Observable<List<ProductEntity>> productEntityList() {
    return this.productCache.getProducts();
  }

  @Override
  public Observable<ProductEntity> productEntityDetails(int id) {
    return this.productCache.get(id);
  }
}
