package com.example.pocketdiaryapp;

import android.content.Context;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.Controller;
import Data.ControllerAPI;
import Data.DataBaseHandler;
import Model.Event;

public class EventSaveFunctions {
    private ControllerAPI controllerAPI;

    public static void save(Context context, EditText editTextName, EditText editTextStartDate, EditText editTextStartTime, EditText editTextEndDate, EditText editTextEndTime, EditText editNameLocation, EditText editCategories){

        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        //System.out.println(editTextDate.getText().toString());
        Date date = null;
        String formattedDate1 = null;
        String formattedDate2 = null;
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formater.parse(editTextStartDate.getText().toString());
            formattedDate1 = targetFormat.format(date);
            date = formater.parse(editTextEndDate.getText().toString());
            formattedDate2 = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Event event = new Event(
                editTextName.getText().toString(),
                formattedDate1,
                editTextStartTime.getText().toString(),
                formattedDate2,
                editTextEndTime.getText().toString(),
                editNameLocation.getText().toString(),
                editCategories.getText().toString());

        //saveOnDB(context, event);

        ControllerAPI controllerAPI = new ControllerAPI();
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        controllerAPI.addEvent(eventList);
    }


    private static void saveOnDB(Context context, Event event){
        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        dataBaseHandler.addEvent(event);
    }

}
