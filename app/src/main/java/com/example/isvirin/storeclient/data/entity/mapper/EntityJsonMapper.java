/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.isvirin.storeclient.data.entity.mapper;

import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.UserEntity;
import com.example.isvirin.storeclient.data.entity.daoconverter.CategoryType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class EntityJsonMapper {

    private final Gson gson;

    @Inject
    public EntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link UserEntity}.
     *
     * @param userJsonResponse A json representing a user profile.
     * @return {@link UserEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public UserEntity transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
        try {
            Type userEntityType = new TypeToken<UserEntity>() {
            }.getType();
            UserEntity userEntity = this.gson.fromJson(userJsonResponse, userEntityType);
            return userEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    public ProductEntity transformProductEntity(String userJsonResponse) throws JsonSyntaxException {
        try {
            Type productEntityType = new TypeToken<ProductEntity>() {
            }.getType();
            ProductEntity productEntity = this.gson.fromJson(userJsonResponse, productEntityType);
            return productEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link UserEntity}.
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of {@link UserEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<UserEntity> transformUserEntityCollection(String userListJsonResponse)
            throws JsonSyntaxException {

        List<UserEntity> userEntityCollection;
        try {
            Type listOfUserEntityType = new TypeToken<List<UserEntity>>() {
            }.getType();
            userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

            return userEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    public List<ProductEntity> transformProductEntityCollection(String productListJsonResponse)
            throws JsonSyntaxException {

        List<ProductEntity> productEntityCollection;
        try {
            Type listOfProductEntityType = new TypeToken<List<ProductEntity>>() {
            }.getType();
            productEntityCollection = this.gson.fromJson(productListJsonResponse, listOfProductEntityType);
            return productEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    public List<CategoryEntity> transformCategoryEntityCollection(String categoryListJsonResponse)
            throws JSONException {

        List<CategoryEntity> categoryEntityCollection = new ArrayList<>();
        CategoryEntity categoryEntity;
        try {
            JSONArray jsonArray = new JSONArray(categoryListJsonResponse);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                categoryEntity = new CategoryEntity();
                categoryEntity.setCategoryId(jsonObject.getInt("id"));
                categoryEntity.setName(jsonObject.getString("name"));
                categoryEntity.setCategoryType(CategoryType.TEXT);
                categoryEntityCollection.add(categoryEntity);
            }
            return categoryEntityCollection;
        } catch (JSONException jsonException) {
            throw  jsonException;
        }
//    try {
//      Type listOfCategoryEntityType = new TypeToken<List<CategoryEntity>>() {}.getType();
//      categoryEntityCollection = this.gson.fromJson(categoryListJsonResponse, listOfCategoryEntityType);
//      return categoryEntityCollection;
//    } catch (JsonSyntaxException jsonException) {
//      throw jsonException;
//    }
    }
}
