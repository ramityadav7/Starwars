package exercise.android.arrk.starwars.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("people/")
    Call<exercise.android.arrk.starwars.network.model.Response> getCharacters(
            @Query("page") String pageCount
    );
}
