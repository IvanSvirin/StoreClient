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
import com.example.isvirin.storeclient.presentation.internal.di.components.DaggerProductComponent;
import com.example.isvirin.storeclient.presentation.internal.di.components.ProductComponent;
import com.example.isvirin.storeclient.presentation.internal.di.modules.ProductModule;
import com.example.isvirin.storeclient.presentation.model.ProductModel;
import com.example.isvirin.storeclient.presentation.view.fragment.ProductListFragment;

/**
 * Activity that shows a list of Products.
 */
public class ProductListActivity extends BaseActivity implements HasComponent<ProductComponent>, ProductListFragment.ProductListListener {
    private static final String INTENT_EXTRA_PARAM_BRAND_NAME = "INTENT_PARAM_BRAND_NAME";
    private static final String INSTANCE_STATE_PARAM_BRAND_NAME = "STATE_PARAM_BRAND_NAME";

    public static Intent getCallingIntent(Context context, String brandName) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_BRAND_NAME, brandName);
        return intent;
    }

    private String brandName;
    private ProductComponent productComponent;

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
            outState.putString(INSTANCE_STATE_PARAM_BRAND_NAME, this.brandName);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.brandName = getIntent().getStringExtra(INTENT_EXTRA_PARAM_BRAND_NAME);
            addFragment(R.id.fragmentContainer, new ProductListFragment());
        } else {
            this.brandName = savedInstanceState.getString(INSTANCE_STATE_PARAM_BRAND_NAME);
        }
    }

    private void initializeInjector() {
        this.productComponent = DaggerProductComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .productModule(new ProductModule(this.brandName))
                .build();
    }

    @Override
    public ProductComponent getComponent() {
        return productComponent;
    }

    public void onProductClicked(ProductModel productModel) {
        this.navigator.navigateToProductDetails(this, productModel.getId());
    }
}
