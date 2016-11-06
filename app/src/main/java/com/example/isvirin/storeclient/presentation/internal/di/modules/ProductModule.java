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
package com.example.isvirin.storeclient.presentation.internal.di.modules;

import com.example.isvirin.storeclient.domain.executor.PostExecutionThread;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;
import com.example.isvirin.storeclient.domain.interactor.GetProductDetails;
import com.example.isvirin.storeclient.domain.interactor.GetProductList;
import com.example.isvirin.storeclient.domain.interactor.UseCase;
import com.example.isvirin.storeclient.domain.repository.ProductRepository;
import com.example.isvirin.storeclient.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class ProductModule {
    private int id = -1;

    public ProductModule() {
    }

    public ProductModule(int id) {
        this.id = id;
    }

    @Provides
    @PerActivity
    @Named("productList")
    UseCase provideGetProductListUseCase(GetProductList getProductList) {
        return getProductList;
    }

    @Provides
    @PerActivity
    @Named("productDetails")
    UseCase provideGetProductDetailsUseCase(ProductRepository productRepository, ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread) {
        return new GetProductDetails(id, productRepository, threadExecutor, postExecutionThread);
    }
}