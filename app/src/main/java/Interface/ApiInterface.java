package Interface;

import java.util.List;

import Model.EventDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("get_all")
    Call<List<EventDTO>> getAllEvent();

    @GET("get_one")
    Call<List<EventDTO>> getOneEvent(@Query("id") int id);

    @POST("add")
    Call<List<EventDTO>> addEvent(@Body List<EventDTO> eventAPI);

    @PUT("updt")
    Call<List<EventDTO>> updateEvent(@Body List<EventDTO> eventAPI);


    @DELETE("dell_one")
    Call<List<EventDTO>> deleteEvent(@Query("id") int id);


}

