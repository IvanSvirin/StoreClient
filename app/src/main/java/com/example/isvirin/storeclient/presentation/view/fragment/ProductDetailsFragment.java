/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.isvirin.storeclient.R;
import com.example.isvirin.storeclient.presentation.internal.di.components.ProductComponent;
import com.example.isvirin.storeclient.presentation.model.ProductModel;
import com.example.isvirin.storeclient.presentation.presenter.ProductDetailsPresenter;
import com.example.isvirin.storeclient.presentation.view.ProductDetailsView;
import com.example.isvirin.storeclient.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows details of a certain user.
 */
public class ProductDetailsFragment extends BaseFragment implements ProductDetailsView {

  @Inject
  ProductDetailsPresenter productDetailsPresenter;

  @Bind(R.id.iv_cover)
  AutoLoadImageView iv_cover;
  @Bind(R.id.tv_name)
  TextView tv_name;
  @Bind(R.id.tv_brand)
  TextView tv_brand;
  @Bind(R.id.tv_price)
  TextView tv_price;
  @Bind(R.id.tv_description)
  TextView tv_description;
  @Bind(R.id.rl_progress)
  RelativeLayout rl_progress;
  @Bind(R.id.rl_retry)
  RelativeLayout rl_retry;
  @Bind(R.id.bt_retry)
  Button bt_retry;

  public ProductDetailsFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(ProductComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_product_details, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.productDetailsPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadProductDetails();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.productDetailsPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.productDetailsPresenter.pause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.productDetailsPresenter.destroy();
  }

  public void renderProduct(ProductModel productModel) {
    if (productModel != null) {
      this.tv_name.setText(productModel.getName());
      this.tv_brand.setText(productModel.getBrand());
      this.tv_price.setText(String.valueOf(productModel.getPrice()));
      this.tv_description.setText(productModel.getDescription());
    }
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

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override
  public Context context() {
    return getActivity().getApplicationContext();
  }

  /**
   * Loads all products.
   */
  private void loadProductDetails() {
    if (this.productDetailsPresenter != null) {
      this.productDetailsPresenter.initialize();
    }
  }

  @OnClick(R.id.bt_retry)
  void onButtonRetryClick() {
    ProductDetailsFragment.this.loadProductDetails();
  }

}
