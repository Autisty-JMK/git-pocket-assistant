package Data;

import android.util.Log;
import android.widget.EditText;

import com.example.pocketdiaryapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Interface.ApiEventInterface;
import Interface.ApiInterface;
import Model.Event;
import Model.EventAPI;
import Model.ResponAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerAPI implements Callback<List<Event>> {

    static final String BASE_URL = "http://192.168.1.107:8080/api/";
    private List<Event> respEventList;
    private EditText textView;
    private Call<List<Event>> call = null;
    private ApiEventInterface apiEventInterface;
    private ResponAPI responAPI;


    public ControllerAPI(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiEventInterface = retrofit.create(ApiEventInterface.class);

    }

    public ResponAPI getAllEvent(){
        call = apiEventInterface.getAllEvent();
        call.enqueue(this);
        return responAPI;
    };

    public ResponAPI  getOneEvent(Integer id){
        call = apiEventInterface.getOneEvent(id);
        call.enqueue(this);
        return responAPI;
    };

    public ResponAPI addEvent(List<Event> eventlist){
        call = apiEventInterface.addEvent(eventlist);
        call.enqueue(this);
        return responAPI;
    };

    public ResponAPI updateEvent(List<Event> eventlist){
        call = apiEventInterface.updateEvent(eventlist);
        call.enqueue(this);
        return responAPI;
    };

    public ResponAPI deleteEvent(Integer id){
        call = apiEventInterface.deleteEvent(id);
        call.enqueue(this);
        return responAPI;
    };

    @Override
    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        if(response.isSuccessful() | response!=null){
            responAPI = new ResponAPI(true, response.body());
            Log.d("api: ", "Ответ и объект получен от сервера" );
            Log.d("api: ", respEventList.toString() );
        }else if(response.isSuccessful() | response==null){
            responAPI = new ResponAPI(true, null);
            Log.d("api: ", "Ответ без объекта получен от сервера" );
        }
    }

    @Override
    public void onFailure(Call<List<Event>> call, Throwable throwable) {
        responAPI = new ResponAPI(true, null);
        Log.d("api: ", "Ошибка вызова запроса:  " + throwable );
    }
}


 /*public void start (Integer id){


        //Log.d("api: ", "Старт прошел" );

        List<Event> eventlist = new ArrayList<>();
        switch (id) {
            case R.id.btd_api_get_all:
                call = apiEventInterface.getAllEvent();
                break;
            case R.id.btn_api_get_one:
                call = apiEventInterface.getOneEvent(3);
                break;
            case R.id.btn_api_add:
                eventlist.clear();
                eventlist.add(new Event("android2","android3", "ndroid4"));
                eventlist.add(new Event("ndroid7","ndroid5", "android6"));
                call = apiEventInterface.addEvent(eventlist);
                break;
            case R.id.btn_api_updt:
                eventlist.clear();
                eventlist.add(new Event(5,"upd-name-android5","upd-tdate-android", "0000"));
                call = apiEventInterface.updateEvent(eventlist);
                break;
            case R.id.btn_api_del:
                call = apiEventInterface.deleteEvent(3);
                break;

        }
        call.enqueue(this);

    }*/

