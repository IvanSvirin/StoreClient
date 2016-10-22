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

import com.example.isvirin.storeclient.domain.Category;
import com.example.isvirin.storeclient.domain.exception.DefaultErrorBundle;
import com.example.isvirin.storeclient.domain.exception.ErrorBundle;
import com.example.isvirin.storeclient.domain.interactor.DefaultSubscriber;
import com.example.isvirin.storeclient.domain.interactor.UseCase;
import com.example.isvirin.storeclient.presentation.exception.ErrorMessageFactory;
import com.example.isvirin.storeclient.presentation.internal.di.PerActivity;
import com.example.isvirin.storeclient.presentation.mapper.ModelDataMapper;
import com.example.isvirin.storeclient.presentation.model.CategoryModel;
import com.example.isvirin.storeclient.presentation.view.CategoryListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class CategoryListPresenter implements Presenter {

    private CategoryListView viewListView;

    private final UseCase getCategoryListUseCase;
    private final ModelDataMapper categoryModelDataMapper;

    @Inject
    public CategoryListPresenter(@Named("categoryList") UseCase getCategoryListUserCase, ModelDataMapper categoryModelDataMapper) {
        this.getCategoryListUseCase = getCategoryListUserCase;
        this.categoryModelDataMapper = categoryModelDataMapper;
    }

    public void setView(@NonNull CategoryListView view) {
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
        this.getCategoryListUseCase.unsubscribe();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the category list.
     */
    public void initialize() {
        this.loadCategoryList();
    }

    /**
     * Loads all categories.
     */
    private void loadCategoryList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCategoryList();
    }

    public void onCategoryClicked(CategoryModel categoryModel) {
        this.viewListView.viewCategory(categoryModel);
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

    private void showCategoriesCollectionInView(Collection<Category> categoryCollection) {
        final Collection<CategoryModel> categoryModelCollection =
                this.categoryModelDataMapper.transformCategories(categoryCollection);
        this.viewListView.renderCategoryList(categoryModelCollection);
    }

    private void getCategoryList() {
        this.getCategoryListUseCase.execute(new CategoryListSubscriber());
    }

    private final class CategoryListSubscriber extends DefaultSubscriber<List<Category>> {

        @Override
        public void onCompleted() {
            CategoryListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CategoryListPresenter.this.hideViewLoading();
            CategoryListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            CategoryListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Category> categories) {
            CategoryListPresenter.this.showCategoriesCollectionInView(categories);
        }
    }
}
