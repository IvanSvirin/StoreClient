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

import com.example.isvirin.storeclient.data.entity.BrandEntity;
import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.daoconverter.BrandType;
import com.example.isvirin.storeclient.data.entity.daoconverter.CategoryType;
import com.example.isvirin.storeclient.data.entity.daoconverter.ProductType;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

//    public ProductEntity transformProductEntity(String userJsonResponse) throws JsonSyntaxException {
//        try {
//            Type productEntityType = new TypeToken<ProductEntity>() {
//            }.getType();
//            ProductEntity productEntity = this.gson.fromJson(userJsonResponse, productEntityType);
//            return productEntity;
//        } catch (JsonSyntaxException jsonException) {
//            throw jsonException;
//        }
//    }

    public List<ProductEntity> transformProductEntityCollection(String productListJsonResponse) throws JSONException {
        List<ProductEntity> productEntityCollection = new ArrayList<>();
        ProductEntity productEntity;
        try {
            JSONArray jsonArray = new JSONArray(productListJsonResponse);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                productEntity = new ProductEntity();
                productEntity.setProductId(jsonObject.getInt("id"));
                productEntity.setName(jsonObject.getString("name"));
                productEntity.setCategoryId(jsonObject.getString("category_id"));
                productEntity.setCode(jsonObject.getString("code"));
                productEntity.setPrice(jsonObject.getString("price"));
                productEntity.setBrand(jsonObject.getString("brand"));
                productEntity.setDescription(jsonObject.getString("description"));
                productEntity.setProductType(ProductType.TEXT);
                productEntityCollection.add(productEntity);
            }
            return productEntityCollection;
        } catch (JSONException jsonException) {
            throw jsonException;
        }
    }

    public List<CategoryEntity> transformCategoryEntityCollection(String categoryListJsonResponse) throws JSONException {
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
            throw jsonException;
        }
    }

    public List<BrandEntity> transformBrandEntityCollection(String brandListJsonResponse) throws JSONException {
        List<BrandEntity> brandEntityCollection = new ArrayList<>();
        BrandEntity brandEntity;
        try {
            JSONArray jsonArray = new JSONArray(brandListJsonResponse);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                brandEntity = new BrandEntity();
                brandEntity.setBrandId(jsonObject.getInt("id"));
                brandEntity.setCategoryId(jsonObject.getInt("category_id"));
                brandEntity.setName(jsonObject.getString("name"));
                brandEntity.setBrandType(BrandType.TEXT);
                brandEntityCollection.add(brandEntity);
            }
            return brandEntityCollection;
        } catch (JSONException jsonException) {
            throw jsonException;
        }
    }
}
