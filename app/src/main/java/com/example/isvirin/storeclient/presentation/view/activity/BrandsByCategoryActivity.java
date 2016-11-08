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
import com.example.isvirin.storeclient.presentation.internal.di.components.BrandComponent;
import com.example.isvirin.storeclient.presentation.internal.di.components.DaggerBrandComponent;
import com.example.isvirin.storeclient.presentation.internal.di.modules.BrandModule;
import com.example.isvirin.storeclient.presentation.model.BrandModel;
import com.example.isvirin.storeclient.presentation.view.fragment.BrandListFragment;

public class BrandsByCategoryActivity extends BaseActivity implements HasComponent<BrandComponent>,
        BrandListFragment.BrandListListener {
    private static final String INTENT_EXTRA_PARAM_CATEGORY_ID = "INTENT_PARAM_CATEGORY_ID";
    private static final String INSTANCE_STATE_PARAM_CATEGORY_ID = "STATE_PARAM_CATEGORY_ID";

    public static Intent getCallingIntent(Context context, int id) {
        Intent intent = new Intent(context, BrandsByCategoryActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_CATEGORY_ID, id);
        return intent;
    }

    private int id;
    private BrandComponent brandComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CATEGORY_ID, this.id);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.id = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CATEGORY_ID, -1);
            addFragment(R.id.fragmentContainer, new BrandListFragment());
        } else {
            this.id = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CATEGORY_ID);
        }
    }

    private void initializeInjector() {
        this.brandComponent = DaggerBrandComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .brandModule(new BrandModule(this.id))
                .build();
    }

    @Override
    public BrandComponent getComponent() {
        return brandComponent;
    }

    public void onBrandClicked(BrandModel brandModel) {
        this.navigator.navigateToProductList(this, brandModel.getId());
    }
}
