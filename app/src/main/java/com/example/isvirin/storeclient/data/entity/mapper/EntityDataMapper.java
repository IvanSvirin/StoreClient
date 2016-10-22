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
package com.example.isvirin.storeclient.data.entity.mapper;

import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.UserEntity;
import com.example.isvirin.storeclient.domain.Category;
import com.example.isvirin.storeclient.domain.Product;
import com.example.isvirin.storeclient.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class EntityDataMapper {
  @Inject
  public EntityDataMapper() {}

  /**
   * Transform a {@link UserEntity} into an {@link User}.
   *
   * @param userEntity Object to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public User transform(UserEntity userEntity) {
    User user = null;
    if (userEntity != null) {
      user = new User(userEntity.getUserId());
      user.setCoverUrl(userEntity.getCoverUrl());
      user.setFullName(userEntity.getFullname());
      user.setDescription(userEntity.getDescription());
      user.setFollowers(userEntity.getFollowers());
      user.setEmail(userEntity.getEmail());
    }
    return user;
  }

  public Product transform(ProductEntity productEntity) {
    Product product = null;
    if (productEntity != null) {
      product = new Product(productEntity.getId());
      product.setName(productEntity.getName());
      product.setCategoryId(productEntity.getCategoryId());
      product.setCode(productEntity.getCode());
      product.setPrice(productEntity.getPrice());
      product.setBrand(productEntity.getBrand());
      product.setDescription(productEntity.getDescription());
    }
    return product;
  }

  public Category transform(CategoryEntity categoryEntity) {
    Category category = null;
    if (categoryEntity != null) {
      category = new Category();
      category.setId(categoryEntity.getId());
      category.setName(categoryEntity.getName());
    }
    return category;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public List<User> transform(Collection<UserEntity> userEntityCollection) {
    List<User> userList = new ArrayList<>(20);
    User user;
    for (UserEntity userEntity : userEntityCollection) {
      user = transform(userEntity);
      if (user != null) {
        userList.add(user);
      }
    }
    return userList;
  }

  public List<Product> transformProducts(Collection<ProductEntity> productEntityCollection) {
    List<Product> products = new ArrayList<>(20);
    Product product;
    for (ProductEntity productEntity : productEntityCollection) {
      product = transform(productEntity);
      if (product != null) {
        products.add(product);
      }
    }
    return products;
  }

  public List<Category> transformCategories(Collection<CategoryEntity> categoryEntityCollection) {
    List<Category> categories = new ArrayList<>(20);
    Category category;
    for (CategoryEntity categoryEntity : categoryEntityCollection) {
      category = transform(categoryEntity);
      if (category != null) {
        categories.add(category);
      }
    }
    return categories;
  }
}
