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
import com.example.isvirin.storeclient.presentation.view.fragment.ProductDetailsFragment;

/**
 * Activity that shows details of a certain user.
 */
public class ProductDetailsActivity extends BaseActivity implements HasComponent<ProductComponent> {
  private static final String INTENT_EXTRA_PARAM_PRODUCT_ID = "INTENT_PARAM_PRODUCT_ID";
  private static final String INSTANCE_STATE_PARAM_PRODUCT_ID = "STATE_PARAM_PRODUCT_ID";

  public static Intent getCallingIntent(Context context, int id) {
    Intent callingIntent = new Intent(context, ProductDetailsActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_PRODUCT_ID, id);
    return callingIntent;
  }

  private int id;
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
      outState.putInt(INSTANCE_STATE_PARAM_PRODUCT_ID, this.id);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.id = getIntent().getIntExtra(INTENT_EXTRA_PARAM_PRODUCT_ID, -1);
      addFragment(R.id.fragmentContainer, new ProductDetailsFragment());
    } else {
      this.id = savedInstanceState.getInt(INSTANCE_STATE_PARAM_PRODUCT_ID);
    }
  }

  private void initializeInjector() {
    this.productComponent = DaggerProductComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .productModule(new ProductModule(this.id))
        .build();
  }

  @Override
  public ProductComponent getComponent() {
    return productComponent;
  }
}
