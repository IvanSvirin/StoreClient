/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isvirin.storeclient.R;
import com.example.isvirin.storeclient.presentation.model.ProductModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProductModel}.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

  public interface OnItemClickListener {
    void onProductItemClicked(ProductModel productModel);
  }

  private List<ProductModel> productsCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  public ProductsAdapter(Context context) {
    this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.productsCollection = Collections.emptyList();
  }

  @Override
  public int getItemCount() {
    return (this.productsCollection != null) ? this.productsCollection.size() : 0;
  }

  @Override
  public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.row_product, parent, false);
    return new ProductsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ProductsViewHolder holder, final int position) {
    final ProductModel productModel = this.productsCollection.get(position);
    holder.textViewTitle.setText(productModel.getName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (ProductsAdapter.this.onItemClickListener != null) {
          ProductsAdapter.this.onItemClickListener.onProductItemClicked(productModel);
        }
      }
    });
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setProductsCollection(Collection<ProductModel> productsCollection) {
    this.validateProductsCollection(productsCollection);
    this.productsCollection = (List<ProductModel>) productsCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateProductsCollection(Collection<ProductModel> productsCollection) {
    if (productsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class ProductsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.title)
    TextView textViewTitle;

    public ProductsViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
