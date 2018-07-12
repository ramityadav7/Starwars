package exercise.android.arrk.starwars.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import exercise.android.arrk.starwars.BR;
import exercise.android.arrk.starwars.BaseActivity;
import exercise.android.arrk.starwars.R;
import exercise.android.arrk.starwars.application.StarwarsApplication;
import exercise.android.arrk.starwars.constants.AppConstants;
import exercise.android.arrk.starwars.data.HomeData;
import exercise.android.arrk.starwars.data.HomeDataItem;
import exercise.android.arrk.starwars.detail.DetailActivity;
import exercise.android.arrk.starwars.utils.AppUtil;

public class HomeActivity extends BaseActivity implements HomeAdapter.HomeItemClickHandler, View.OnClickListener{

    private HomeData homeData;
    private HomeAdapter homeAdapter;
    private int lastVisibleItem, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setToolBar(false);

        initializeView();
        viewDataBinding.setVariable(BR.homeData, homeData);
        loadData();
    }

    private void initializeView() {
        homeData = new HomeData();
        List<HomeDataItem> homeDataItems = new ArrayList<>();
        homeAdapter = new HomeAdapter(homeDataItems, this);
        homeData.setHomeDataItems(homeDataItems);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerViewHome = findViewById(R.id.recyclerViewHome);
        recyclerViewHome.setLayoutManager(mLayoutManager);
        recyclerViewHome.setAdapter(homeAdapter);

        recyclerViewHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayoutManager;
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!homeData.isShowPageProgress() && homeData.isShowMore() && totalItemCount <= (lastVisibleItem+1)) {
                    onLoadMore();
                }
            }
        });

        findViewById(R.id.buttonReload).setOnClickListener(this);
    }

    private void loadData()
    {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.setApplication((StarwarsApplication) getApplication());
        homeViewModel.getItems().observe(this, new Observer<HomeData>() {
            @Override
            public void onChanged(@Nullable HomeData homeData) {
                HomeActivity.this.homeData.setShowProgress(homeData.isShowProgress());
                HomeActivity.this.homeData.setShowError(homeData.isShowError());
                HomeActivity.this.homeData.setShowMore(homeData.isShowMore());
                HomeActivity.this.homeData.setShowPageProgress(homeData.isShowPageProgress());
                List<HomeDataItem> homeDataItems = homeData.getHomeDataItems();
                if(!AppUtil.isCollectionEmpty(homeDataItems)) {
                    List<HomeDataItem> data = HomeActivity.this.homeData.getHomeDataItems();
                    data.clear();
                    data.addAll(homeDataItems);
                    homeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void onLoadMore() {
        Log.d("Ramit", "onLoadMore");
        homeData.setShowPageProgress(true);
        homeData.getHomeDataItems().add(null);
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.loadNextPage();
    }

    @Override
    public void onHomeItemClick(int position) {
        HomeDataItem homeDataItem = homeData.getHomeDataItems().get(position);

        Intent myIntent = new Intent(HomeActivity.this, DetailActivity.class);
        myIntent.putExtra(AppConstants.HOME_BUNDLE_DATA_KEY, homeDataItem);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View v) {
        loadData();
    }
}
