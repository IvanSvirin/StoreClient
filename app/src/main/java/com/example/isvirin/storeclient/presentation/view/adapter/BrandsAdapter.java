/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isvirin.storeclient.R;
import com.example.isvirin.storeclient.databinding.RowBrandBinding;
import com.example.isvirin.storeclient.presentation.model.BrandModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.BrandViewHolder> {

  public interface OnItemClickListener {
    void onBrandItemClicked(BrandModel brandModel);
  }

  private List<BrandModel> brandsCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  public BrandsAdapter(Context context) {
    this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.brandsCollection = Collections.emptyList();
  }

  @Override
  public int getItemCount() {
    return (this.brandsCollection != null) ? this.brandsCollection.size() : 0;
  }

  @Override
  public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    RowBrandBinding binding = RowBrandBinding.inflate(inflater, parent, false);
    return new BrandViewHolder(binding.getRoot());
  }

  @Override
  public void onBindViewHolder(BrandViewHolder holder, final int position) {
    final BrandModel brandModel = this.brandsCollection.get(position);
    holder.binding.setBrand(brandModel);
    holder.binding.setClick(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (BrandsAdapter.this.onItemClickListener != null) {
          BrandsAdapter.this.onItemClickListener.onBrandItemClicked(brandModel);
        }
      }
    });
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setBrandsCollection(Collection<BrandModel> brandsCollection) {
    this.validateBrandsCollection(brandsCollection);
    this.brandsCollection = (List<BrandModel>) brandsCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateBrandsCollection(Collection<BrandModel> brandCollection) {
    if (brandCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class BrandViewHolder extends RecyclerView.ViewHolder {
    RowBrandBinding binding;

    public BrandViewHolder(View itemView) {
      super(itemView);
      binding = DataBindingUtil.bind(itemView);
    }
  }
}
