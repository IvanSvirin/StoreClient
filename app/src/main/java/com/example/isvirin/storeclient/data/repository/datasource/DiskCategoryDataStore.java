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


import com.example.isvirin.storeclient.data.cache.CategoryCache;
import com.example.isvirin.storeclient.data.entity.CategoryEntity;

import java.util.List;

import rx.Observable;

/**
 * {@link CategoryDataStore} implementation based on file system data store.
 */
class DiskCategoryDataStore implements CategoryDataStore {

  private final CategoryCache categoryCache;

  /**
   * Construct a {@link CategoryDataStore} based file system data store.
   *
   * @param categoryCache A {@link CategoryCache} to cache data retrieved from the api.
   */
  DiskCategoryDataStore(CategoryCache categoryCache) {
    this.categoryCache = categoryCache;
  }

  @Override
  public Observable<List<CategoryEntity>> categoryEntityList() {
    return this.categoryCache.getCategories();
  }
}
