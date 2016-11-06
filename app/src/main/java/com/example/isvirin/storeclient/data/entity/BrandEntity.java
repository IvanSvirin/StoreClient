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

import com.example.isvirin.storeclient.data.entity.daoconverter.BrandType;
import com.example.isvirin.storeclient.data.entity.daoconverter.BrandTypeConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(indexes = {
        @Index(value = "name ASC", unique = true)
})
public class BrandEntity {
    @Id
    private Long id;

    @NotNull
    @SerializedName("id")
    private int brandId;

    @NotNull
    @SerializedName("category_id")
    private int categoryId;

    @NotNull
    @SerializedName("name")
    private String name;

    @Convert(converter = BrandTypeConverter.class, columnType = String.class)
    private BrandType brandType;

    @Generated(hash = 2056783902)
    public BrandEntity() {
    }

    public BrandEntity(Long id) {
        this.id = id;
    }

    @Generated(hash = 506945594)
    public BrandEntity(Long id, int brandId, int categoryId, @NotNull String name, BrandType brandType) {
        this.id = id;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.brandType = brandType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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

    public BrandType getBrandType() {
        return brandType;
    }

    public void setBrandType(BrandType brandType) {
        this.brandType = brandType;
    }
}
