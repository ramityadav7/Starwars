package exercise.android.arrk.starwars.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import exercise.android.arrk.starwars.BR;
import exercise.android.arrk.starwars.R;
import exercise.android.arrk.starwars.data.HomeDataItem;
import exercise.android.arrk.starwars.utils.AppUtil;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<HomeDataItem> homeDataItems;
    private HomeItemClickHandler homeItemClickHandler;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public interface HomeItemClickHandler {
        void onHomeItemClick(int position);
    }

    public HomeAdapter(List<HomeDataItem> homeDataItems, HomeItemClickHandler homeItemClickHandler)
    {
        this.homeDataItems = homeDataItems;
        this.homeItemClickHandler = homeItemClickHandler;
    }

    @Override
    public int getItemViewType(int position) {
        return homeDataItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return AppUtil.isCollectionEmpty(homeDataItems) ? 0 : homeDataItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == VIEW_TYPE_ITEM) {
            ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.home_item, parent, false);
            viewHolder =  new HomeItemHolder(binding);
        } else if(viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_loading, parent, false);
            viewHolder = new LoadingHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HomeItemHolder) {
            HomeItemHolder homeItemHolder = (HomeItemHolder) holder;
            HomeDataItem homeDataItem = homeDataItems.get(position);
            homeItemHolder.bind(homeDataItem);

            View view = homeItemHolder.getBinding().getRoot();
            view.setTag(position);
            view.setOnClickListener(this);
        } else if(holder instanceof LoadingHolder) {
            LoadingHolder loadingViewHolder = (LoadingHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public class HomeItemHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public HomeItemHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void bind(Object object) {
            binding.setVariable(BR.homeDataItem, object);
        }
    }

    public class LoadingHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public void onClick(View v) {
        homeItemClickHandler.onHomeItemClick((int)v.getTag());
    }
}
