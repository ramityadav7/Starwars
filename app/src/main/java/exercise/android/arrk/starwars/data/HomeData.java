package exercise.android.arrk.starwars.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

import exercise.android.arrk.starwars.BR;

public class HomeData extends BaseObservable{

    private List<HomeDataItem> homeDataItems;
//    private boolean isNetworkAvailable;
    private boolean showProgress;
    private boolean showError;
    private boolean showMore;
    private boolean showPageProgress;

    public List<HomeDataItem> getHomeDataItems() {
        return homeDataItems;
    }

    public void setHomeDataItems(List<HomeDataItem> homeDataItems) {
        this.homeDataItems = homeDataItems;
    }

//    public boolean isNetworkAvailable() {
//        return isNetworkAvailable;
//    }
//
//    public void setNetworkAvailable(boolean networkAvailable) {
//        isNetworkAvailable = networkAvailable;
//    }

    @Bindable
    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        notifyPropertyChanged(BR.showProgress);
    }

    @Bindable
    public boolean isShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError = showError;
        notifyPropertyChanged(BR.showError);
    }

    public boolean isShowMore() {
        return showMore;
    }

    public void setShowMore(boolean showMore) {
        this.showMore = showMore;
    }

    public boolean isShowPageProgress() {
        return showPageProgress;
    }

    public void setShowPageProgress(boolean showPageProgress) {
        this.showPageProgress = showPageProgress;
    }
}
