/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.isvirin.storeclient.R;
import com.example.isvirin.storeclient.presentation.internal.di.HasComponent;
import com.example.isvirin.storeclient.presentation.internal.di.components.CategoryComponent;
import com.example.isvirin.storeclient.presentation.internal.di.components.DaggerCategoryComponent;
import com.example.isvirin.storeclient.presentation.model.CategoryModel;
import com.example.isvirin.storeclient.presentation.view.fragment.CategoryListFragment;

/**
 * Activity that shows a list of Categories.
 */
public class CategoryListActivity extends BaseActivity implements HasComponent<CategoryComponent>,
        CategoryListFragment.CategoryListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CategoryListActivity.class);
    }

    private CategoryComponent categoryComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new CategoryListFragment());
        }
    }

    private void initializeInjector() {
        this.categoryComponent = DaggerCategoryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public CategoryComponent getComponent() {
        return categoryComponent;
    }

    public void onCategoryClicked(CategoryModel categoryModel) {
        this.navigator.navigateToProductList(this, categoryModel.getId());
    }
}
