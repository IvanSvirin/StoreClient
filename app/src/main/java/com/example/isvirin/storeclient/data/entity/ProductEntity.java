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

import com.example.isvirin.storeclient.data.entity.daoconverter.ProductType;
import com.example.isvirin.storeclient.data.entity.daoconverter.ProductTypeConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Product Entity used in the data layer.
 */
@Entity(indexes = {
        @Index(value = "productId, categoryId, brand ASC", unique = true)
})
public class ProductEntity {
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

  @Convert(converter = ProductTypeConverter.class, columnType = String.class)
  private ProductType productType;

  @Generated(hash = 27353230)
  public ProductEntity() {
  }

  public ProductEntity(Long id) {
    this.id = id;
  }

  @Generated(hash = 945859737)
  public ProductEntity(Long id, int productId, @NotNull String name, @NotNull String categoryId, @NotNull String code,
          @NotNull String price, @NotNull String brand, @NotNull String description, ProductType productType) {
      this.id = id;
      this.productId = productId;
      this.name = name;
      this.categoryId = categoryId;
      this.code = code;
      this.price = price;
      this.brand = brand;
      this.description = description;
      this.productType = productType;
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

  public ProductType getProductType() {
    return productType;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }
}
