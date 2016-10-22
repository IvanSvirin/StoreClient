/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view;


import com.example.isvirin.storeclient.presentation.model.ProductModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link ProductModel}.
 */
public interface ProductListView extends LoadDataView {
    /**
     * Render a user list in the UI.
     *
     * @param productModelCollection The collection of {@link ProductModel} that will be shown.
     */
    void renderProductList(Collection<ProductModel> productModelCollection);

    /**
     * View a {@link ProductModel} profile/details.
     *
     * @param productModel The user that will be shown.
     */
    void viewProduct(ProductModel productModel);
}
