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
package com.example.isvirin.storeclient.domain.interactor;

import com.example.isvirin.storeclient.domain.Category;
import com.example.isvirin.storeclient.domain.executor.PostExecutionThread;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;
import com.example.isvirin.storeclient.domain.repository.CategoryRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Category}.
 */
public class GetCategoryList extends UseCase {

  private final CategoryRepository categoryRepository;

  @Inject
  public GetCategoryList(CategoryRepository categoryRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.categoryRepository = categoryRepository;
  }

  @Override public Observable buildUseCaseObservable() {
    return this.categoryRepository.categories();
  }
}
