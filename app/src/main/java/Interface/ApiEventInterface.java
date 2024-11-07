package Interface;

import java.time.LocalDate;
import java.util.List;

import Model.Event;
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
    Call<List<Event>> getAllEvent();

    @GET("get_one")
    Call<List<Event>> getOneEvent(@Query("id") int id);

    @GET("get_perion")
    Call<List<Event>> getPeriodEvend(@Path("date1")String date1, @Path("date2")String date2);

    @POST("add")
    Call<List<Event>> addEvent(@Body List<Event> eventAPI);

    @PUT("updt")
    Call<List<Event>> updateEvent(@Body List<Event> eventAPI);


    @DELETE("dell_one")
    Call<List<Event>> deleteEvent(@Query("id") int id);

}
