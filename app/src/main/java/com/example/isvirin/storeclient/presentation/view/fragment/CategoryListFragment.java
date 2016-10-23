package com.example.isvirin.storeclient.presentation.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.isvirin.storeclient.R;
import com.example.isvirin.storeclient.presentation.internal.di.components.CategoryComponent;
import com.example.isvirin.storeclient.presentation.model.CategoryModel;
import com.example.isvirin.storeclient.presentation.presenter.CategoryListPresenter;
import com.example.isvirin.storeclient.presentation.view.CategoryListView;
import com.example.isvirin.storeclient.presentation.view.adapter.CategoriesAdapter;
import com.example.isvirin.storeclient.presentation.view.adapter.CommonLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryListFragment extends BaseFragment implements CategoryListView {
    /**
     * Interface for listening category list events.
     */
    public interface CategoryListListener {
        void onCategoryClicked(final CategoryModel categoryModel);
    }

    @Inject
    CategoryListPresenter categoryListPresenter;
    @Inject
    CategoriesAdapter categoriesAdapter;

    @Bind(R.id.rv_categories)
    RecyclerView rv_categories;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private CategoryListFragment.CategoryListListener categoryListListener;

    public CategoryListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CategoryListFragment.CategoryListListener) {
            this.categoryListListener = (CategoryListFragment.CategoryListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CategoryComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.categoryListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadCategoryList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.categoryListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.categoryListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_categories.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.categoryListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.categoryListListener = null;
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    public void renderCategoryList(Collection<CategoryModel> categoryModelCollection) {
        if (categoryModelCollection != null) {
            this.categoriesAdapter.setCategoriesCollection(categoryModelCollection);
        }
    }

    public void viewCategory(CategoryModel categoryModel) {
        if (this.categoryListListener != null) {
            this.categoryListListener.onCategoryClicked(categoryModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.categoriesAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_categories.setLayoutManager(new CommonLayoutManager(context()));
        this.rv_categories.setAdapter(categoriesAdapter);
    }

    /**
     * Loads all users.
     */
    private void loadCategoryList() {
        this.categoryListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        CategoryListFragment.this.loadCategoryList();
    }

    private CategoriesAdapter.OnItemClickListener onItemClickListener =
            new CategoriesAdapter.OnItemClickListener() {
                public void onCategoryItemClicked(CategoryModel categoryModel) {
                    if (CategoryListFragment.this.categoryListPresenter != null && categoryModel != null) {
                        CategoryListFragment.this.categoryListPresenter.onCategoryClicked(categoryModel);
                    }
                }
            };
}
