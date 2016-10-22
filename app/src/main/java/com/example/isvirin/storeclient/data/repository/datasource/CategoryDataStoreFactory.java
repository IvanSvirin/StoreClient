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

import com.example.isvirin.storeclient.data.cache.CategoryCache;
import com.example.isvirin.storeclient.data.entity.mapper.EntityJsonMapper;
import com.example.isvirin.storeclient.data.net.RestApi;
import com.example.isvirin.storeclient.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link CategoryDataStore}.
 */
@Singleton
public class CategoryDataStoreFactory {

  private final Context context;
  private final CategoryCache categoryCache;

  @Inject
  public CategoryDataStoreFactory(@NonNull Context context, @NonNull CategoryCache categoryCache) {
    this.context = context.getApplicationContext();
    this.categoryCache = categoryCache;
  }

  public CategoryDataStore createList() {
    CategoryDataStore categoryDataStore;
    if (!this.categoryCache.isExpired() && this.categoryCache.isCategoriesCached()) {
      categoryDataStore = new DiskCategoryDataStore(this.categoryCache);
    } else {
      categoryDataStore = createCloudDataStore();
    }
    return categoryDataStore;
  }

  /**
   * Create {@link CategoryDataStore} to retrieve data from the Cloud.
   */
  public CategoryDataStore createCloudDataStore() {
    EntityJsonMapper entityJsonMapper = new EntityJsonMapper();
    RestApi restApi = new RestApiImpl(this.context, entityJsonMapper);
    return new CloudCategoryDataStore(restApi, this.categoryCache);
  }
}
