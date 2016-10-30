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
package com.example.isvirin.storeclient.data.entity;

import com.example.isvirin.storeclient.data.entity.daoconverter.CategoryType;
import com.example.isvirin.storeclient.data.entity.daoconverter.CategoryTypeConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Category Entity used in the data layer.
 */
@Entity(indexes = {
        @Index(value = "name ASC", unique = true)
})
public class CategoryEntity {
    @Id
    private Long id;

    @NotNull
    @SerializedName("id")
    private int categoryId;

    @NotNull
    @SerializedName("name")
    private String name;

    @Convert(converter = CategoryTypeConverter.class, columnType = String.class)
    private CategoryType categoryType;

    @Generated(hash = 725894750)
    public CategoryEntity() {
    }

    public CategoryEntity(Long id) {
        this.id = id;
    }

    @Generated(hash = 784349189)
    public CategoryEntity(Long id, int categoryId, @NotNull String name, CategoryType categoryType) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.categoryType = categoryType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** User Entity Details *****\n");
        stringBuilder.append("categoryId=" + this.getCategoryId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
