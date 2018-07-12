package exercise.android.arrk.starwars.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import exercise.android.arrk.starwars.BaseActivity;
import exercise.android.arrk.starwars.R;
import exercise.android.arrk.starwars.constants.AppConstants;
import exercise.android.arrk.starwars.data.HomeDataItem;

public class DetailActivity extends BaseActivity{
    private TextView textViewName;
    private TextView textViewHeight;
    private TextView textViewMass;
    private TextView textViewDate;
    private TextView textViewTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setToolBar(true);

        HomeDataItem homeDataItem = getIntent().getExtras().getParcelable(AppConstants.HOME_BUNDLE_DATA_KEY);
        initializeView();
        populateData(homeDataItem);
    }

    private void initializeView() {
        textViewName = findViewById(R.id.textViewName);
        textViewHeight = findViewById(R.id.textViewHeight);
        textViewMass = findViewById(R.id.textViewMass);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
    }

    private void populateData(HomeDataItem homeDataItem) {
        textViewName.setText(homeDataItem.getName());
        textViewHeight.setText(homeDataItem.getHeight());
        textViewMass.setText(homeDataItem.getMass());
        textViewDate.setText(homeDataItem.getDate());
        textViewTime.setText(homeDataItem.getTime());
    }
}
