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
package com.example.isvirin.storeclient.data.entity;

import com.example.isvirin.storeclient.data.entity.daoconverter.ChosenItemType;
import com.example.isvirin.storeclient.data.entity.daoconverter.ChosenItemTypeConverter;
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
public class ChosenItemEntity {
  @Id
  private Long id;

  @NotNull
  @SerializedName("id")
  private int productId;

  @NotNull
  @SerializedName("name")
  private String name;

  @NotNull
  @SerializedName("category_id")
  private String categoryId;

  @NotNull
  @SerializedName("code")
  private String code;

  @NotNull
  @SerializedName("price")
  private String price;

  @NotNull
  @SerializedName("brand")
  private String brand;

  @NotNull
  @SerializedName("description")
  private String description;

  @Convert(converter = ChosenItemTypeConverter.class, columnType = String.class)
  private ChosenItemType chosenItemType;

  @Generated(hash = 96011975)
  public ChosenItemEntity() {
  }

  public ChosenItemEntity(Long id) {
    this.id = id;
  }

  @Generated(hash = 1386515465)
  public ChosenItemEntity(Long id, int productId, @NotNull String name, @NotNull String categoryId, @NotNull String code,
          @NotNull String price, @NotNull String brand, @NotNull String description, ChosenItemType chosenItemType) {
      this.id = id;
      this.productId = productId;
      this.name = name;
      this.categoryId = categoryId;
      this.code = code;
      this.price = price;
      this.brand = brand;
      this.description = description;
      this.chosenItemType = chosenItemType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ChosenItemType getChosenItemType() {
    return chosenItemType;
  }

  public void setChosenItemType(ChosenItemType chosenItemType) {
    this.chosenItemType = chosenItemType;
  }
}
