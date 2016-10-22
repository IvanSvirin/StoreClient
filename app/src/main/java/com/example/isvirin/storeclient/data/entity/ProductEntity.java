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

import com.google.gson.annotations.SerializedName;

/**
 * Product Entity used in the data layer.
 */
public class ProductEntity {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("category_id")
  private String categoryId;

  @SerializedName("code")
  private String code;

  @SerializedName("price")
  private String price;

  @SerializedName("brand")
  private String brand;

  @SerializedName("description")
  private String description;

  public ProductEntity() {
    //empty
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** User Entity Details *****\n");
    stringBuilder.append("id=" + this.getId() + "\n");
    stringBuilder.append("name=" + this.getName() + "\n");
    stringBuilder.append("category_id=" + this.getCategoryId() + "\n");
    stringBuilder.append("code=" + this.getCode() + "\n");
    stringBuilder.append("price=" + this.getPrice() + "\n");
    stringBuilder.append("brand=" + this.getBrand() + "\n");
    stringBuilder.append("description=" + this.getDescription() + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
