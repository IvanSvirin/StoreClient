package com.example.isvirin.storeclient.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Button;


import com.example.isvirin.storeclient.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

  @Bind(R.id.btn_LoadData)
  Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData)
  void navigateToUserList() {
    this.navigator.navigateToCategoryList(this);
//    this.navigator.navigateToUserList(this);
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    return false;
  }
}
