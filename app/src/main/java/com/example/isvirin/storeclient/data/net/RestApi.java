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


import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.UserEntity;

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
//  String API_BASE_URL = "http://www.android10.org/myapi/";
  String API_BASE_URL = "http://myshop/api/?apitestmine.";

  /** Api url for getting all users */
  String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";
  /** Api url for getting all products */
  String API_URL_GET_PRODUCTS = API_BASE_URL + "getProducts={}";
  /** Api url for getting all categories */
  String API_URL_GET_CATEGORIES = API_BASE_URL + "getCategories={}";
  /** Api url for getting a product by id */
  String API_URL_GET_PRODUCT = API_BASE_URL + "getProductById={\"productId\":";

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link UserEntity}.
   */
  Observable<List<UserEntity>> userEntityList();

  /**
   * Retrieves an {@link rx.Observable} which will emit a {@link UserEntity}.
   *
   * @param userId The user id used to get user data.
   */
  Observable<UserEntity> userEntityById(final int userId);


  Observable<List<ProductEntity>> productEntityList();

  Observable<List<CategoryEntity>> categoryEntityList();

  Observable<ProductEntity> productEntityById(final int id);
}
