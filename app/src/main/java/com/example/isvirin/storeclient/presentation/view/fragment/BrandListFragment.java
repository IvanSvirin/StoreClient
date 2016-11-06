package com.example.isvirin.storeclient.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.isvirin.storeclient.R;
import com.example.isvirin.storeclient.presentation.internal.di.components.BrandComponent;
import com.example.isvirin.storeclient.presentation.model.BrandModel;
import com.example.isvirin.storeclient.presentation.presenter.BrandsByCategoryPresenter;
import com.example.isvirin.storeclient.presentation.view.BrandListView;
import com.example.isvirin.storeclient.presentation.view.adapter.BrandsAdapter;
import com.example.isvirin.storeclient.presentation.view.adapter.CommonLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BrandListFragment extends BaseFragment implements BrandListView {

    public interface BrandListListener {
        void onBrandClicked(final BrandModel brandModel);
    }

    @Inject
    BrandsByCategoryPresenter brandsByCategoryPresenter;
    @Inject
    BrandsAdapter brandsAdapter;

    @Bind(R.id.rv_brands)
    RecyclerView rv_brands;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private BrandListFragment.BrandListListener brandListListener;

    public BrandListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BrandListFragment.BrandListListener) {
            this.brandListListener = (BrandListFragment.BrandListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(BrandComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_brand_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.brandsByCategoryPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadBrandList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.brandsByCategoryPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.brandsByCategoryPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_brands.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.brandsByCategoryPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.brandListListener = null;
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    public void renderBrandList(Collection<BrandModel> brandModelCollection) {
        if (brandModelCollection != null) {
            this.brandsAdapter.setBrandsCollection(brandModelCollection);
        }
    }

    public void viewBrand(BrandModel brandModel) {
        if (this.brandListListener != null) {
            this.brandListListener.onBrandClicked(brandModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.brandsAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_brands.setLayoutManager(new CommonLayoutManager(context()));
        this.rv_brands.setAdapter(brandsAdapter);
    }

    private void loadBrandList() {
        this.brandsByCategoryPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        BrandListFragment.this.loadBrandList();
    }

    private BrandsAdapter.OnItemClickListener onItemClickListener =
            new BrandsAdapter.OnItemClickListener() {
                public void onBrandItemClicked(BrandModel brandModel) {
                    if (BrandListFragment.this.brandsByCategoryPresenter != null && brandModel != null) {
                        BrandListFragment.this.brandsByCategoryPresenter.onBrandClicked(brandModel);
                    }
                }
            };
}

