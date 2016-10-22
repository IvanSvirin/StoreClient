/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
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
import com.example.isvirin.storeclient.presentation.view.ProductDetailsView;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ProductDetailsPresenter implements Presenter {

  private ProductDetailsView viewDetailsView;

  private final UseCase getProductDetailsUseCase;
  private final ModelDataMapper productModelDataMapper;

  @Inject
  public ProductDetailsPresenter(@Named("productDetails") UseCase getProductDetailsUseCase, ModelDataMapper productModelDataMapper) {
    this.getProductDetailsUseCase = getProductDetailsUseCase;
    this.productModelDataMapper = productModelDataMapper;
  }

  public void setView(@NonNull ProductDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    this.getProductDetailsUseCase.unsubscribe();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by start retrieving product details.
   */
  public void initialize() {
    this.loadProductDetails();
  }

  /**
   * Loads user details.
   */
  private void loadProductDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getProductDetails();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(), errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showProductDetailsInView(Product product) {
    final ProductModel productModel = this.productModelDataMapper.transform(product);
    this.viewDetailsView.renderProduct(productModel);
  }

  private void getProductDetails() {
    this.getProductDetailsUseCase.execute(new ProductDetailsSubscriber());
  }

  @RxLogSubscriber
  private final class ProductDetailsSubscriber extends DefaultSubscriber<Product> {

    @Override
    public void onCompleted() {
      ProductDetailsPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      ProductDetailsPresenter.this.hideViewLoading();
      ProductDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      ProductDetailsPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(Product product) {
      ProductDetailsPresenter.this.showProductDetailsInView(product);
    }
  }
}
