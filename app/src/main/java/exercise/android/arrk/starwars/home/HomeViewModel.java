package exercise.android.arrk.starwars.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import exercise.android.arrk.starwars.application.StarwarsApplication;
import exercise.android.arrk.starwars.data.HomeData;
import exercise.android.arrk.starwars.data.HomeDataItem;
import exercise.android.arrk.starwars.network.model.Response;
import exercise.android.arrk.starwars.network.model.ResultsItem;
import exercise.android.arrk.starwars.utils.AppUtil;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeViewModel extends ViewModel{

    private StarwarsApplication starwarsApplication;
    private MutableLiveData<HomeData> homeLiveData;
    private int pageCount = 1;

    public void setApplication(StarwarsApplication starwarsApplication) {
        this.starwarsApplication = starwarsApplication;
    }

    public LiveData<HomeData> getItems() {
        if (homeLiveData == null || homeLiveData.getValue().isShowError()) {
            if(homeLiveData == null)
                homeLiveData = new MutableLiveData<>();
            HomeData homeData = new HomeData();
            homeData.setShowProgress(true);
            homeData.setShowError(false);
            homeLiveData.setValue(homeData);

            loadHomeData();
        }

        return homeLiveData;
    }

    public void loadNextPage() {
        pageCount++;
        loadHomeData();
    }

    private void loadHomeData() {
        Call<Response> call = starwarsApplication.getApiService().getCharacters(String.valueOf(pageCount));

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {
                    Response response1 = response.body();
                    HomeData homeData = homeLiveData.getValue();
                    homeData.setShowProgress(false);
                    homeData.setShowPageProgress(false);
                    homeData.setShowMore(!TextUtils.isEmpty(response1.getNext()));
                    homeLiveData.setValue(parseHomeData(response1.getResults(), homeData));
                    Log.d("Ramit", response1.toString());
                } else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                handleFailure();
            }
        });


    }

    private void handleFailure() {
        HomeData homeData = new HomeData();
        homeData.setShowProgress(false);
        homeData.setShowError(true);
        homeData.setShowPageProgress(false);
        homeLiveData.setValue(homeData);
        Log.d("Ramit", "Failure");
    }

    private HomeData parseHomeData(List<ResultsItem> resultsItems, HomeData homeData) {
        List<HomeDataItem> homeDataItems = new ArrayList<>();

        for(ResultsItem resultsItem: resultsItems) {
            HomeDataItem homeDataItem = new HomeDataItem();
            homeDataItem.setName(resultsItem.getName());
            homeDataItem.setHeight(AppUtil.convertCmToMeter(resultsItem.getHeight()));
            homeDataItem.setMass(resultsItem.getMass());
            homeDataItem.setDate(AppUtil.getDateString(resultsItem.getCreated()));
            homeDataItem.setTime(AppUtil.getTimeString(resultsItem.getCreated()));
            homeDataItems.add(homeDataItem);
        }

        List<HomeDataItem> previousData = homeData.getHomeDataItems();
        if(AppUtil.isCollectionEmpty(previousData)) {
            homeData.setHomeDataItems(homeDataItems);
        } else {
            previousData.addAll(homeDataItems);
        }

        return homeData;
    }
}
