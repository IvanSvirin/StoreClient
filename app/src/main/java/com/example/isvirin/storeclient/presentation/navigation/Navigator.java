/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.isvirin.storeclient.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.isvirin.storeclient.presentation.view.activity.BrandsByCategoryActivity;
import com.example.isvirin.storeclient.presentation.view.activity.CategoryListActivity;
import com.example.isvirin.storeclient.presentation.view.activity.ProductDetailsActivity;
import com.example.isvirin.storeclient.presentation.view.activity.ProductListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {
  @Inject
  public Navigator() {
    //empty
  }

  /**
   * Goes to the product details screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToProductDetails(Context context, int id) {
    if (context != null) {
      Intent intentToLaunch = ProductDetailsActivity.getCallingIntent(context, id);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the product list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */

  public void navigateToProductList(Context context, int id) {
    Intent intentToLaunch = ProductListActivity.getCallingIntent(context);
    context.startActivity(intentToLaunch);
  }

  public void navigateToCategoryList(Context context) {
    Intent intentToLaunch = CategoryListActivity.getCallingIntent(context);
    context.startActivity(intentToLaunch);
  }

  public void navigateToBrandList(Context context, int id) {
    Intent intentToLaunch = BrandsByCategoryActivity.getCallingIntent(context, id);
    context.startActivity(intentToLaunch);
  }
}
