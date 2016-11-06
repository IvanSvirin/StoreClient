/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.isvirin.storeclient.presentation.view;


import com.example.isvirin.storeclient.presentation.model.BrandModel;

import java.util.Collection;

public interface BrandListView extends LoadDataView {

    void renderBrandList(Collection<BrandModel> brandModelCollection);

    void viewBrand(BrandModel brandModel);
}
