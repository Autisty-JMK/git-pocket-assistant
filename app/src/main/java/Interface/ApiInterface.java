package Interface;

import java.util.List;

import Model.EventAPI;
import retrofit2.Call;
import retrofit.http.GET;

public interface ApiInterface {

    @GET("get_all")
    Call<List<EventAPI>> getAllEvent();
}

