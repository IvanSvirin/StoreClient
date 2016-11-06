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
package com.example.isvirin.storeclient.presentation.mapper;


import com.example.isvirin.storeclient.domain.Brand;
import com.example.isvirin.storeclient.domain.Category;
import com.example.isvirin.storeclient.domain.Product;
import com.example.isvirin.storeclient.presentation.internal.di.PerActivity;
import com.example.isvirin.storeclient.presentation.model.BrandModel;
import com.example.isvirin.storeclient.presentation.model.CategoryModel;
import com.example.isvirin.storeclient.presentation.model.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Product} (in the domain layer) to {@link ProductModel} in the
 * presentation layer.
 */
@PerActivity
public class ModelDataMapper {

  @Inject
  public ModelDataMapper() {}

  public ProductModel transform(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    ProductModel productModel = new ProductModel(product.getId());
    productModel.setName(product.getName());
    productModel.setCategoryId(product.getCategoryId());
    productModel.setCode(product.getCode());
    productModel.setPrice(product.getPrice());
    productModel.setBrand(product.getBrand());
    productModel.setDescription(product.getDescription());
    return productModel;
  }

  public CategoryModel transform(Category category) {
    if (category == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    CategoryModel categoryModel = new CategoryModel();
    categoryModel.setId(category.getId());
    categoryModel.setName(category.getName());
    return categoryModel;
  }

  public BrandModel transform(Brand brand) {
    if (brand == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    BrandModel brandModel = new BrandModel();
    brandModel.setId(brand.getId());
    brandModel.setCategoryId(brand.getCategoryId());
    brandModel.setName(brand.getName());
    return brandModel;
  }

  public Collection<ProductModel> transformProducts(Collection<Product> productCollection) {
    Collection<ProductModel> productModelCollection;

    if (productCollection != null && !productCollection.isEmpty()) {
      productModelCollection = new ArrayList<>();
      for (Product product : productCollection) {
        productModelCollection.add(transform(product));
      }
    } else {
      productModelCollection = Collections.emptyList();
    }
    return productModelCollection;
  }

  public Collection<CategoryModel> transformCategories(Collection<Category> categoryCollection) {
    Collection<CategoryModel> categoryModelCollection;

    if (categoryCollection != null && !categoryCollection.isEmpty()) {
      categoryModelCollection = new ArrayList<>();
      for (Category category : categoryCollection) {
        categoryModelCollection.add(transform(category));
      }
    } else {
      categoryModelCollection = Collections.emptyList();
    }
    return categoryModelCollection;
  }

  public Collection<BrandModel> transformBrands(Collection<Brand> brandCollection) {
    Collection<BrandModel> brandModelCollection;
    if (brandCollection != null && !brandCollection.isEmpty()) {
      brandModelCollection = new ArrayList<>();
      for (Brand brand : brandCollection) {
        brandModelCollection.add(transform(brand));
      }
    } else {
      brandModelCollection = Collections.emptyList();
    }
    return brandModelCollection;
  }
}
