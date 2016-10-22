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
package com.example.isvirin.storeclient.presentation.presenter;

import android.support.annotation.NonNull;

import com.example.isvirin.storeclient.domain.Product;
import com.example.isvirin.storeclient.domain.exception.DefaultErrorBundle;
import com.example.isvirin.storeclient.domain.exception.ErrorBundle;
import com.example.isvirin.storeclient.domain.interactor.DefaultSubscriber;
import com.example.isvirin.storeclient.domain.interactor.UseCase;
import com.example.isvirin.storeclient.presentation.exception.ErrorMessageFactory;
import com.example.isvirin.storeclient.presentation.internal.di.PerActivity;
import com.example.isvirin.storeclient.presentation.mapper.ModelDataMapper;
import com.example.isvirin.storeclient.presentation.model.ProductModel;
import com.example.isvirin.storeclient.presentation.view.ProductListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ProductListPresenter implements Presenter {

    private ProductListView viewListView;

    private final UseCase getProductListUseCase;
    private final ModelDataMapper productModelDataMapper;

    @Inject
    public ProductListPresenter(@Named("productList") UseCase getProductListUserCase, ModelDataMapper productModelDataMapper) {
        this.getProductListUseCase = getProductListUserCase;
        this.productModelDataMapper = productModelDataMapper;
    }

    public void setView(@NonNull ProductListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getProductListUseCase.unsubscribe();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the product list.
     */
    public void initialize() {
        this.loadProductList();
    }

    /**
     * Loads all products.
     */
    private void loadProductList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getProductList();
    }

    public void onProductClicked(ProductModel productModel) {
        this.viewListView.viewProduct(productModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showProductsCollectionInView(Collection<Product> productCollection) {
        final Collection<ProductModel> productModelCollection =
                this.productModelDataMapper.transformProducts(productCollection);
        this.viewListView.renderProductList(productModelCollection);
    }

    private void getProductList() {
        this.getProductListUseCase.execute(new ProductListSubscriber());
    }

    private final class ProductListSubscriber extends DefaultSubscriber<List<Product>> {

        @Override
        public void onCompleted() {
            ProductListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            ProductListPresenter.this.hideViewLoading();
            ProductListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ProductListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Product> products) {
            ProductListPresenter.this.showProductsCollectionInView(products);
        }
    }
}
