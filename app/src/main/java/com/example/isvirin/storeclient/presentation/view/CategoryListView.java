/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view;


import com.example.isvirin.storeclient.presentation.model.CategoryModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link CategoryModel}.
 */
public interface CategoryListView extends LoadDataView {
    /**
     * Render a user list in the UI.
     *
     * @param categoryModelCollection The collection of {@link CategoryModel} that will be shown.
     */
    void renderCategoryList(Collection<CategoryModel> categoryModelCollection);

    /**
     * View a {@link CategoryModel} profile/details.
     *
     * @param categoryModel The user that will be shown.
     */
    void viewCategory(CategoryModel categoryModel);
}
