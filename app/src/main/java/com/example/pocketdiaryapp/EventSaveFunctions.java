package com.example.pocketdiaryapp;

import android.content.Context;
import android.os.Build;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import Data.ControllerAPI;
import Model.EventDTO;
import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Date;

public class EventSaveFunctions {
    private ControllerAPI controllerAPI;

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
                formattedDateTimeStart = LocalDateTime.parse(formattedDateStart + "T" + editTextStartTime.getText().toString() + ":00");
                formattedDateTimeEnd = LocalDateTime.parse(formattedDateEnd + "T" + editTextEndTime.getText().toString() + ":00");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventDTO event = new EventDTO(
                editTextName.getText().toString(),
                formattedDateTimeStart,
                formattedDateTimeEnd,
                editNameLocation.getText().toString(),
                editCategories.getText().toString());

        //saveOnDB(context, event);

        ControllerAPI controllerAPI = new ControllerAPI();
        List<EventDTO> eventList = new ArrayList<>();
        eventList.add(event);
        controllerAPI.addEvent(eventList);

    }


  /*  private static void saveOnDB(Context context, Event event){
        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        dataBaseHandler.addEvent(event);
    }
*/

}
