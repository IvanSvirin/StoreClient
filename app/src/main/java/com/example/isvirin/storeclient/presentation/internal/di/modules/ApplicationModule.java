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
package com.example.isvirin.storeclient.presentation.internal.di.modules;

import android.content.Context;

import com.example.isvirin.storeclient.data.cache.BrandCache;
import com.example.isvirin.storeclient.data.cache.BrandCacheImpl;
import com.example.isvirin.storeclient.data.cache.CategoryCache;
import com.example.isvirin.storeclient.data.cache.CategoryCacheImpl;
import com.example.isvirin.storeclient.data.cache.ProductCache;
import com.example.isvirin.storeclient.data.cache.ProductCacheImpl;
import com.example.isvirin.storeclient.data.executor.JobExecutor;
import com.example.isvirin.storeclient.data.repository.BrandDataRepository;
import com.example.isvirin.storeclient.data.repository.CategoryDataRepository;
import com.example.isvirin.storeclient.data.repository.ProductDataRepository;
import com.example.isvirin.storeclient.domain.executor.PostExecutionThread;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;
import com.example.isvirin.storeclient.domain.repository.BrandRepository;
import com.example.isvirin.storeclient.domain.repository.CategoryRepository;
import com.example.isvirin.storeclient.domain.repository.ProductRepository;
import com.example.isvirin.storeclient.presentation.AndroidApplication;
import com.example.isvirin.storeclient.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  ProductCache provideProductCache(ProductCacheImpl productCache) {
    return productCache;
  }

  @Provides
  @Singleton
  CategoryCache provideCategoryCache(CategoryCacheImpl categoryCache) {
    return categoryCache;
  }

  @Provides
  @Singleton
  BrandCache provideBrandCache(BrandCacheImpl brandCache) {
    return brandCache;
  }

  @Provides
  @Singleton
  ProductRepository provideProductRepository(ProductDataRepository productDataRepository) {
    return productDataRepository;
  }

  @Provides
  @Singleton
  CategoryRepository provideCategoryRepository(CategoryDataRepository categoryDataRepository) {
    return categoryDataRepository;
  }

  @Provides
  @Singleton
  BrandRepository provideBrandRepository(BrandDataRepository brandDataRepository) {
    return brandDataRepository;
  }
}
