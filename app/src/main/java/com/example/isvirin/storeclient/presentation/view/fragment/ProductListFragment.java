package com.example.isvirin.storeclient.presentation.view.fragment;

import android.content.Context;

import com.example.isvirin.storeclient.presentation.model.ProductModel;
import com.example.isvirin.storeclient.presentation.view.ProductListView;

import java.util.Collection;

public class ProductListFragment extends BaseFragment implements ProductListView {
    /**
     * Interface for listening product list events.
     */
    public interface ProductListListener {
        void onProductClicked(final ProductModel productModel);
    }

    @Override
    public void renderProductList(Collection<ProductModel> productModelCollection) {

    }

    @Override
    public void viewProduct(ProductModel productModel) {

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
