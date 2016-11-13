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
import com.example.isvirin.storeclient.data.repository.datasource.ProductDataStore;
import com.example.isvirin.storeclient.data.repository.datasource.ProductDataStoreFactory;
import com.example.isvirin.storeclient.domain.Product;
import com.example.isvirin.storeclient.domain.repository.ProductRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link ProductRepository} for retrieving user data.
 */
@Singleton
public class ProductDataRepository implements ProductRepository {

  private final ProductDataStoreFactory productDataStoreFactory;
  private final EntityDataMapper entityDataMapper;

  /**
   * Constructs a {@link ProductRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param entityDataMapper {@link EntityDataMapper}.
   */
  @Inject
  public ProductDataRepository(ProductDataStoreFactory dataStoreFactory, EntityDataMapper entityDataMapper) {
    this.productDataStoreFactory = dataStoreFactory;
    this.entityDataMapper = entityDataMapper;
  }

  @Override
  public Observable<List<Product>> products() {
    final ProductDataStore productDataStore = this.productDataStoreFactory.createList();
    return productDataStore.productEntityList().map(this.entityDataMapper::transformProducts);
  }

  @Override
  public Observable<List<Product>> productsByBrand(String brandName) {
    final ProductDataStore productDataStore = this.productDataStoreFactory.createListByBrand(brandName);
    return productDataStore.productEntitiesByBrand(brandName).map(this.entityDataMapper::transformProducts);
  }

  @Override
  public Observable<Product> product(int id) {
    final ProductDataStore productDataStore = this.productDataStoreFactory.create(id);
    return productDataStore.productEntityDetails(id).map(this.entityDataMapper::transform);
  }
}
