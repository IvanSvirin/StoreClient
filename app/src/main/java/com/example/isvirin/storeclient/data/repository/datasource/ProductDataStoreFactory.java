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

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.isvirin.storeclient.data.cache.ProductCache;
import com.example.isvirin.storeclient.data.entity.mapper.EntityJsonMapper;
import com.example.isvirin.storeclient.data.net.RestApi;
import com.example.isvirin.storeclient.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link ProductDataStore}.
 */
@Singleton
public class ProductDataStoreFactory {

  private final Context context;
  private final ProductCache productCache;

  @Inject
  public ProductDataStoreFactory(@NonNull Context context, @NonNull ProductCache productCache) {
    this.context = context.getApplicationContext();
    this.productCache = productCache;
  }

  /**
   * Create {@link ProductDataStore} from a user id.
   */
  public ProductDataStore create(int id) {
    ProductDataStore productDataStore;

    if (!this.productCache.isExpired() && this.productCache.isCached(id)) {
      productDataStore = new DiskProductDataStore(this.productCache);
    } else {
      productDataStore = createCloudDataStore();
    }
    return productDataStore;
  }

  public ProductDataStore createList() {
    ProductDataStore productDataStore;
    if (!this.productCache.isExpired() && this.productCache.isProductsCached()) {
      productDataStore = new DiskProductDataStore(this.productCache);
    } else {
      productDataStore = createCloudDataStore();
    }
    return productDataStore;
  }

  /**
   * Create {@link ProductDataStore} to retrieve data from the Cloud.
   */
  public ProductDataStore createCloudDataStore() {
    EntityJsonMapper entityJsonMapper = new EntityJsonMapper();
    RestApi restApi = new RestApiImpl(this.context, entityJsonMapper);
    return new CloudProductDataStore(restApi, this.productCache);
  }
}
