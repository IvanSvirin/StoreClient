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
package com.example.isvirin.storeclient.domain.interactor;

import com.example.isvirin.storeclient.domain.executor.PostExecutionThread;
import com.example.isvirin.storeclient.domain.executor.ThreadExecutor;
import com.example.isvirin.storeclient.domain.repository.ProductRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetProductsByBrand extends UseCase {
    private final String brandName;
    private final ProductRepository productRepository;

    @Inject
    public GetProductsByBrand(String brandName, ProductRepository productRepository, ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.brandName = brandName;
        this.productRepository = productRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.productRepository.productsByBrand(this.brandName);
    }
}
