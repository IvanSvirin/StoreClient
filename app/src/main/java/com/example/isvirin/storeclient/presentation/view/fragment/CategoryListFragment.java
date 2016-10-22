package com.example.isvirin.storeclient.presentation.view.fragment;


import android.content.Context;

import com.example.isvirin.storeclient.presentation.model.CategoryModel;
import com.example.isvirin.storeclient.presentation.view.CategoryListView;

import java.util.Collection;

public class CategoryListFragment extends BaseFragment implements CategoryListView {
    /**
     * Interface for listening category list events.
     */
    public interface CategoryListListener {
        void onCategoryClicked(final CategoryModel categoryModel);
    }

    @Override
    public void renderCategoryList(Collection<CategoryModel> categoryModelCollection) {

    }

    @Override
    public void viewCategory(CategoryModel categoryModel) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }
}
