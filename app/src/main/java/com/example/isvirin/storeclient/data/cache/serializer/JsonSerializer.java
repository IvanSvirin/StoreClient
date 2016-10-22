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
package com.example.isvirin.storeclient.data.cache.serializer;

import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class user as Serializer/Deserializer for all entities.
 */
@Singleton
public class JsonSerializer {
  private final Gson gson = new Gson();

  @Inject
  public JsonSerializer() {}

  /**
   * Serialize an object to Json.
   *
   * @param userEntity {@link UserEntity} to serialize.
   */
  public String serialize(UserEntity userEntity) {
    String jsonString = gson.toJson(userEntity, UserEntity.class);
    return jsonString;
  }

  public String serializeProduct(ProductEntity productEntity) {
    String jsonString = gson.toJson(productEntity, ProductEntity.class);
    return jsonString;
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param jsonString A json string to deserialize.
   * @return {@link UserEntity}
   */
  public UserEntity deserialize(String jsonString) {
    UserEntity userEntity = gson.fromJson(jsonString, UserEntity.class);
    return userEntity;
  }

  public ProductEntity deserializeProduct(String jsonString) {
    ProductEntity productEntity = gson.fromJson(jsonString, ProductEntity.class);
    return productEntity;
  }

  public String serializeList(List<UserEntity> userEntity) {
    String jsonString = gson.toJson(userEntity);
    return jsonString;
  }

  public String serializeProducts(List<ProductEntity> productEntities) {
    String jsonString = gson.toJson(productEntities);
    return jsonString;
  }

  public String serializeCategories(List<CategoryEntity> categoryEntities) {
    String jsonString = gson.toJson(categoryEntities);
    return jsonString;
  }

  public List<UserEntity> deserializeList(String jsonString) {
    Type listOfUserEntityType = new TypeToken<List<UserEntity>>() {}.getType();
    return gson.fromJson(jsonString, listOfUserEntityType);
  }

  public List<ProductEntity> deserializeProducts(String jsonString) {
    Type listOfProductEntityType = new TypeToken<List<ProductEntity>>() {}.getType();
    return gson.fromJson(jsonString, listOfProductEntityType);
  }

  public List<CategoryEntity> deserializeCategories(String jsonString) {
    Type listOfCategoryEntityType = new TypeToken<List<CategoryEntity>>() {}.getType();
    return gson.fromJson(jsonString, listOfCategoryEntityType);
  }
}
