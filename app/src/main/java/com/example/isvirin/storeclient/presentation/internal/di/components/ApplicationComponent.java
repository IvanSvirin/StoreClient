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
package com.example.isvirin.storeclient.presentation.internal.di.components;

import android.content.Context;

import com.example.isvirin.storeclient.domain.executor.PostExecutionThread;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;
import com.example.isvirin.storeclient.domain.repository.BrandRepository;
import com.example.isvirin.storeclient.domain.repository.CategoryRepository;
import com.example.isvirin.storeclient.domain.repository.ProductRepository;
import com.example.isvirin.storeclient.presentation.internal.di.modules.ApplicationModule;
import com.example.isvirin.storeclient.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  ProductRepository productRepository();
  CategoryRepository categoryRepository();
  BrandRepository brandRepository();
}
