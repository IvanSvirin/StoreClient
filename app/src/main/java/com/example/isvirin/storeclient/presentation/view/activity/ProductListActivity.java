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
import com.example.isvirin.storeclient.presentation.model.ProductModel;
import com.example.isvirin.storeclient.presentation.view.fragment.ProductListFragment;

/**
 * Activity that shows a list of Products.
 */
public class ProductListActivity extends BaseActivity implements HasComponent<ProductComponent>,
        ProductListFragment.ProductListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ProductListActivity.class);
    }

    private ProductComponent productComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new ProductListFragment());
        }
    }

    private void initializeInjector() {
        this.productComponent = DaggerProductComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
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
