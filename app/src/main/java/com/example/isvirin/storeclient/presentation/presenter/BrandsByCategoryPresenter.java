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

import com.example.isvirin.storeclient.domain.Brand;
import com.example.isvirin.storeclient.domain.exception.DefaultErrorBundle;
import com.example.isvirin.storeclient.domain.exception.ErrorBundle;
import com.example.isvirin.storeclient.domain.interactor.DefaultSubscriber;
import com.example.isvirin.storeclient.domain.interactor.UseCase;
import com.example.isvirin.storeclient.presentation.exception.ErrorMessageFactory;
import com.example.isvirin.storeclient.presentation.internal.di.PerActivity;
import com.example.isvirin.storeclient.presentation.mapper.ModelDataMapper;
import com.example.isvirin.storeclient.presentation.model.BrandModel;
import com.example.isvirin.storeclient.presentation.view.BrandListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class BrandsByCategoryPresenter implements Presenter {
    private BrandListView viewListView;

    private final UseCase getBrandsByCategoryUseCase;
    private final ModelDataMapper brandModelDataMapper;

    @Inject
    public BrandsByCategoryPresenter(@Named("brandsByCategory") UseCase getBrandsByCategoryUseCase, ModelDataMapper brandModelDataMapper) {
        this.getBrandsByCategoryUseCase = getBrandsByCategoryUseCase;
        this.brandModelDataMapper = brandModelDataMapper;
    }

    public void setView(@NonNull BrandListView view) {
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
        this.getBrandsByCategoryUseCase.unsubscribe();
        this.viewListView = null;
    }

    public void initialize() {
        this.loadBrandList();
    }

    /**
     * Loads all categories.
     */
    private void loadBrandList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getBrandList();
    }

    public void onBrandClicked(BrandModel brandModel) {
        this.viewListView.viewBrand(brandModel);
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
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(), errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showBrandsCollectionInView(Collection<Brand> brandCollection) {
        final Collection<BrandModel> brandModelCollection =
                this.brandModelDataMapper.transformBrands(brandCollection);
        this.viewListView.renderBrandList(brandModelCollection);
    }

    private void getBrandList() {
        this.getBrandsByCategoryUseCase.execute(new BrandsByCategorySubscriber());
    }

    private final class BrandsByCategorySubscriber extends DefaultSubscriber<List<Brand>> {

        @Override
        public void onCompleted() {
            BrandsByCategoryPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            BrandsByCategoryPresenter.this.hideViewLoading();
            BrandsByCategoryPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            BrandsByCategoryPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Brand> brands) {
            BrandsByCategoryPresenter.this.showBrandsCollectionInView(brands);
        }
    }
}
