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
package com.example.isvirin.storeclient.data.repository;


import com.example.isvirin.storeclient.data.entity.mapper.EntityDataMapper;
import com.example.isvirin.storeclient.data.repository.datasource.CategoryDataStore;
import com.example.isvirin.storeclient.data.repository.datasource.CategoryDataStoreFactory;
import com.example.isvirin.storeclient.domain.Category;
import com.example.isvirin.storeclient.domain.repository.CategoryRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link CategoryRepository} for retrieving user data.
 */
@Singleton
public class CategoryDataRepository implements CategoryRepository {

  private final CategoryDataStoreFactory categoryDataStoreFactory;
  private final EntityDataMapper entityDataMapper;

  /**
   * Constructs a {@link CategoryRepository}.
   *
   * @param categoryDataStoreFactory A factory to construct different data source implementations.
   * @param entityDataMapper {@link EntityDataMapper}.
   */
  @Inject
  public CategoryDataRepository(CategoryDataStoreFactory categoryDataStoreFactory, EntityDataMapper entityDataMapper) {
    this.categoryDataStoreFactory = categoryDataStoreFactory;
    this.entityDataMapper = entityDataMapper;
  }

  @Override
  public Observable<List<Category>> categories() {
    final CategoryDataStore categoryDataStore = this.categoryDataStoreFactory.createList();
    return categoryDataStore.categoryEntityList().map(this.entityDataMapper::transformCategories);
  }
}
