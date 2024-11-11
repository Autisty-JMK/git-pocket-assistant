package com.example.pocketdiaryapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import Data.ControllerAPI;
import Model.EventDTO;
import Model.EventDTO2;

import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Date;

public class EventSaveFunctions {
    private ControllerAPI controllerAPI;
    public static Timestamp timestampStart = null;
    public static Timestamp timestampEnd = null;

    public static void save(Context context, EditText editTextName, EditText editTextStartDate, EditText editTextStartTime, EditText editTextEndDate, EditText editTextEndTime, EditText editNameLocation, EditText editCategories){

        LocalDateTime formattedDateTimeStart = null;
        LocalDateTime formattedDateTimeEnd = null;


        Date date = null;
        String formattedDateStart = null;
        String formattedDateEnd = null;
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        try {

            date = formater.parse(editTextStartDate.getText().toString());
            formattedDateStart = targetFormat.format(date);
            date = formater.parse(editTextEndDate.getText().toString());
            formattedDateEnd = targetFormat.format(date);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formattedDateTimeStart = LocalDateTime.parse(formattedDateStart + " " + editTextStartTime.getText().toString() + ":30");
                formattedDateTimeEnd = LocalDateTime.parse(formattedDateEnd + " " + editTextEndTime.getText().toString() + ":30");

                timestampStart = Timestamp.valueOf(formattedDateTimeStart);
                timestampEnd = Timestamp.valueOf(formattedDateEnd + " " + editTextEndTime.getText().toString() + ":30");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventDTO2 event = new EventDTO2(null,
                editTextName.getText().toString(),
                timestampStart,
                timestampEnd,
                editNameLocation.getText().toString(),
                editCategories.getText().toString());

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String estrinmg = gson.toJson(event);

        //saveOnDB(context, event);
        Log.d("API Gson: ", estrinmg);
        Log.d("API: ", editTextName.getText().toString()+" "+timestampStart.toString()+" "+timestampEnd.toString() +" "+ editNameLocation.getText().toString()+" "+ editCategories.getText().toString());
        ControllerAPI controllerAPI = new ControllerAPI();
        List<EventDTO2> eventList = new ArrayList<>();
        eventList.add(event);
        controllerAPI.addEvent(eventList);

    }


  /*  private static void saveOnDB(Context context, Event event){
        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        dataBaseHandler.addEvent(event);
    }
*/

}
