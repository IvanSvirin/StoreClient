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
package com.example.isvirin.storeclient.data.net;


import com.example.isvirin.storeclient.data.entity.BrandEntity;
import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  String API_BASE_URL = "http://www.sivapi.esy.es/api/?apitestmine.";
//  String API_BASE_URL = "http://www.softomate.net/ext/json/";

  /** Api url for getting all products */
//  String API_URL_GET_PRODUCTS = API_BASE_URL + "products.json";
  String API_URL_GET_PRODUCTS = API_BASE_URL + "getProducts={}";
  /** Api url for getting all categories */
//  String API_URL_GET_CATEGORIES = API_BASE_URL + "categories.json";
  String API_URL_GET_CATEGORIES = API_BASE_URL + "getCategories={}";
  /** Api url for getting all brands */
  String API_URL_GET_BRANDS = API_BASE_URL + "getBrands={}";

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link ProductEntity}.
   */

  Observable<List<ProductEntity>> productEntityList();

  Observable<List<CategoryEntity>> categoryEntityList();

  Observable<List<BrandEntity>> brandEntityList();

  /**
   * Retrieves an {@link rx.Observable} which will emit a {@link ProductEntity}.
   *
   * @param id The user id used to get user data.
   */

//  Observable<ProductEntity> productEntityById(final int id);
}
