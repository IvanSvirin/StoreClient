/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view;

import com.example.isvirin.storeclient.presentation.model.ProductModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a product profile.
 */
public interface ProductDetailsView extends LoadDataView {
  /**
   * Render a product in the UI.
   *
   * @param product The {@link ProductModel} that will be shown.
   */
  void renderProduct(ProductModel product);
}
