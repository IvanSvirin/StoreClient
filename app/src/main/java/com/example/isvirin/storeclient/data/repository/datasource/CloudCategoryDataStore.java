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


import com.example.isvirin.storeclient.data.cache.CategoryCache;
import com.example.isvirin.storeclient.data.cache.ProductCache;
import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.net.RestApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link CategoryDataStore} implementation based on connections to the api (Cloud).
 */
class CloudCategoryDataStore implements CategoryDataStore {

  private final RestApi restApi;
  private final CategoryCache categoryCache;

  private final Action1<List<CategoryEntity>> saveListToCacheAction = categoryEntities -> {
    if (categoryEntities != null) {
      CloudCategoryDataStore.this.categoryCache.putCategories(categoryEntities);
    }
  };

  /**
   * Construct a {@link ProductDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param categoryCache A {@link ProductCache} to cache data retrieved from the api.
   */
  CloudCategoryDataStore(RestApi restApi, CategoryCache categoryCache) {
    this.restApi = restApi;
    this.categoryCache = categoryCache;
  }

  @Override
  public Observable<List<CategoryEntity>> categoryEntityList() {
    return this.restApi.categoryEntityList().doOnNext(saveListToCacheAction);
  }
}
