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
import com.example.isvirin.storeclient.presentation.internal.di.components.ProductComponent;
import com.example.isvirin.storeclient.presentation.model.ProductModel;
import com.example.isvirin.storeclient.presentation.presenter.ProductListPresenter;
import com.example.isvirin.storeclient.presentation.view.ProductListView;
import com.example.isvirin.storeclient.presentation.view.adapter.ProductsAdapter;
import com.example.isvirin.storeclient.presentation.view.adapter.CommonLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductListFragment extends BaseFragment implements ProductListView {
    /**
     * Interface for listening category list events.
     */
    public interface ProductListListener {
        void onProductClicked(final ProductModel productModel);
    }

    @Inject
    ProductListPresenter productListPresenter;
    @Inject
    ProductsAdapter productsAdapter;

    @Bind(R.id.rv_products)
    RecyclerView rv_products;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private ProductListListener productListListener;

    public ProductListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ProductListListener) {
            this.productListListener = (ProductListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ProductComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.productListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadProductList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.productListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.productListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_products.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.productListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.productListListener = null;
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

    public void renderProductList(Collection<ProductModel> productModelCollection) {
        if (productModelCollection != null) {
            this.productsAdapter.setProductsCollection(productModelCollection);
        }
    }

    public void viewProduct(ProductModel productModel) {
        if (this.productListListener != null) {
            this.productListListener.onProductClicked(productModel);
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
        this.productsAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_products.setLayoutManager(new CommonLayoutManager(context()));
        this.rv_products.setAdapter(productsAdapter);
    }

    /**
     * Loads all products.
     */
    private void loadProductList() {
        this.productListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        ProductListFragment.this.loadProductList();
    }

    private ProductsAdapter.OnItemClickListener onItemClickListener =
            new ProductsAdapter.OnItemClickListener() {
                public void onProductItemClicked(ProductModel productModel) {
                    if (ProductListFragment.this.productListPresenter != null && productModel != null) {
                        ProductListFragment.this.productListPresenter.onProductClicked(productModel);
                    }
                }
            };
}
