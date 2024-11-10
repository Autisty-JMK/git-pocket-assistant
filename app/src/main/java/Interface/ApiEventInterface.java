package Interface;

import java.time.LocalDate;
import java.util.List;

import Model.Event;
import Model.EventDTO;
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
    Call<List<EventDTO>> getAllEvent();

    @GET("get_one")
    Call<List<EventDTO>> getOneEvent(@Query("id") int id);

    @GET("get_perion")
    Call<List<EventDTO>> getPeriodEvend(@Path("date1")String date1, @Path("date2")String date2);

    @POST("add")
    Call<List<EventDTO>> addEvent(@Body List<EventDTO> eventAPI);

    @PUT("updt")
    Call<List<EventDTO>> updateEvent(@Body List<EventDTO> eventAPI);


    @DELETE("dell_one")
    Call<List<EventDTO>> deleteEvent(@Query("id") int id);

}
