package exercise.android.arrk.starwars.application;

import android.app.Application;

import exercise.android.arrk.starwars.constants.AppConstants;
import exercise.android.arrk.starwars.network.ApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StarwarsApplication extends Application {

    private ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        initApiService();
    }

    private void initApiService() {
        OkHttpClient client = new OkHttpClient.Builder().build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
