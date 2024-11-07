package Interface;

import java.util.List;

import Model.EventAPI;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("get_all")
    Call<List<EventAPI>> getAllEvent();

    @GET("get_one")
    Call<List<EventAPI>> getOneEvent(@Query("id") int id);

    @POST("add")
    Call<List<EventAPI>> addEvent(@Body List<EventAPI> eventAPI);

    @PUT("updt")
    Call<List<EventAPI>> updateEvent(@Body List<EventAPI> eventAPI);


    @DELETE("dell_one")
    Call<List<EventAPI>> deleteEvent(@Query("id") int id);


}

