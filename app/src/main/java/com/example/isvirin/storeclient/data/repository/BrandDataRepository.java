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
import com.example.isvirin.storeclient.data.repository.datasource.BrandDataStore;
import com.example.isvirin.storeclient.data.repository.datasource.BrandDataStoreFactory;
import com.example.isvirin.storeclient.domain.Brand;
import com.example.isvirin.storeclient.domain.repository.BrandRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class BrandDataRepository implements BrandRepository {
  private final BrandDataStoreFactory brandDataStoreFactory;
  private final EntityDataMapper entityDataMapper;

  @Inject
  public BrandDataRepository(BrandDataStoreFactory dataStoreFactory, EntityDataMapper entityDataMapper) {
    this.brandDataStoreFactory = dataStoreFactory;
    this.entityDataMapper = entityDataMapper;
  }

  @Override
  public Observable<List<Brand>> brands() {
    final BrandDataStore brandDataStore = this.brandDataStoreFactory.createList();
    return brandDataStore.brandEntityList().map(this.entityDataMapper::transformBrands);
  }

  @Override
  public Observable<List<Brand>> brandsByCategory(int id) {
    final BrandDataStore brandDataStore = this.brandDataStoreFactory.createListByCategoryId(id);
    return brandDataStore.brandEntitiesByCategory(id).map(this.entityDataMapper::transformBrands);
  }
}
