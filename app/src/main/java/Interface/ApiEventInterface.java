package Interface;

import java.util.List;

import Model.EventDTO;
import Model.EventDTO2;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEventInterface {

    @GET("get_all")
    Call<List<EventDTO2>> getAllEvent();

    @GET("get_one")
    Call<List<EventDTO2>> getOneEvent(@Query("id") int id);

    @GET("get_perion")
    Call<List<EventDTO2>> getPeriodEvend(@Path("date1")String date1, @Path("date2")String date2);

    @POST("add")
    Call<List<EventDTO2>> addEvent(@Body List<EventDTO2> eventAPI);

    @PUT("updt")
    Call<List<EventDTO2>> updateEvent(@Body List<EventDTO2> eventAPI);


    @DELETE("dell_one")
    Call<List<EventDTO2>> deleteEvent(@Query("id") int id);

}
