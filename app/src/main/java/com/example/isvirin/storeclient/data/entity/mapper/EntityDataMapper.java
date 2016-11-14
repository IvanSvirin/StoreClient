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

import com.example.isvirin.storeclient.data.entity.BrandEntity;
import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ChosenItemEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.domain.Brand;
import com.example.isvirin.storeclient.domain.Category;
import com.example.isvirin.storeclient.domain.ChosenItem;
import com.example.isvirin.storeclient.domain.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ProductEntity} (in the data layer) to {@link Product} in the
 * domain layer.
 */
@Singleton
public class EntityDataMapper {
  @Inject
  public EntityDataMapper() {}

  public Product transform(ProductEntity productEntity) {
    Product product = null;
    if (productEntity != null) {
      product = new Product(productEntity.getProductId());
      product.setName(productEntity.getName());
      product.setCategoryId(productEntity.getCategoryId());
      product.setCode(productEntity.getCode());
      product.setPrice(productEntity.getPrice());
      product.setBrand(productEntity.getBrand());
      product.setDescription(productEntity.getDescription());
    }
    return product;
  }

  public ChosenItem transform(ChosenItemEntity chosenItemEntity) {
    ChosenItem chosenItem = null;
    if (chosenItemEntity != null) {
      chosenItem = new ChosenItem(chosenItemEntity.getProductId());
      chosenItem.setName(chosenItemEntity.getName());
      chosenItem.setCategoryId(chosenItemEntity.getCategoryId());
      chosenItem.setCode(chosenItemEntity.getCode());
      chosenItem.setPrice(chosenItemEntity.getPrice());
      chosenItem.setBrand(chosenItemEntity.getBrand());
      chosenItem.setDescription(chosenItemEntity.getDescription());
    }
    return chosenItem;
  }

  public Category transform(CategoryEntity categoryEntity) {
    Category category = null;
    if (categoryEntity != null) {
      category = new Category();
      category.setId(categoryEntity.getCategoryId());
      category.setName(categoryEntity.getName());
    }
    return category;
  }

  public Brand transform(BrandEntity brandEntity) {
    Brand brand = null;
    if (brandEntity != null) {
      brand = new Brand();
      brand.setId(brandEntity.getBrandId());
      brand.setCategoryId(brandEntity.getCategoryId());
      brand.setName(brandEntity.getName());
    }
    return brand;
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

  public List<ChosenItem> transformChosenItems(Collection<ChosenItemEntity> chosenItemEntityCollection) {
    List<ChosenItem> chosenItems = new ArrayList<>(20);
    ChosenItem chosenItem;
    for (ChosenItemEntity chosenItemEntity : chosenItemEntityCollection) {
      chosenItem = transform(chosenItemEntity);
      if (chosenItem != null) {
        chosenItems.add(chosenItem);
      }
    }
    return chosenItems;
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

  public List<Brand> transformBrands(Collection<BrandEntity> brandEntityCollection) {
    List<Brand> brands = new ArrayList<>(20);
    Brand brand;
    for (BrandEntity brandEntity : brandEntityCollection) {
      brand = transform(brandEntity);
      if (brand != null) {
        brands.add(brand);
      }
    }
    return brands;
  }
}
