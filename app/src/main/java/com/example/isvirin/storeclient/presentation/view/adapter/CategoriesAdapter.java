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
import com.example.isvirin.storeclient.presentation.model.CategoryModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link CategoryModel}.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

  public interface OnItemClickListener {
    void onCategoryItemClicked(CategoryModel categoryModel);
  }

  private List<CategoryModel> categoriesCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  public CategoriesAdapter(Context context) {
    this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.categoriesCollection = Collections.emptyList();
  }

  @Override
  public int getItemCount() {
    return (this.categoriesCollection != null) ? this.categoriesCollection.size() : 0;
  }

  @Override
  public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.row_category, parent, false);
    return new CategoryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CategoryViewHolder holder, final int position) {
    final CategoryModel categoryModel = this.categoriesCollection.get(position);
    holder.textViewTitle.setText(categoryModel.getName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (CategoriesAdapter.this.onItemClickListener != null) {
          CategoriesAdapter.this.onItemClickListener.onCategoryItemClicked(categoryModel);
        }
      }
    });
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setCategoriesCollection(Collection<CategoryModel> categoriesCollection) {
    this.validateCategoriesCollection(categoriesCollection);
    this.categoriesCollection = (List<CategoryModel>) categoriesCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateCategoriesCollection(Collection<CategoryModel> categoryCollection) {
    if (categoryCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class CategoryViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.title)
    TextView textViewTitle;

    public CategoryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
