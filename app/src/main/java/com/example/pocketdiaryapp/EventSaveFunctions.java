package com.example.pocketdiaryapp;

import android.content.Context;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Data.DataBaseHandler;
import Model.Event;

public class EventSaveFunctions {

    public static void save(Context context, EditText editTextDate,EditText editTextStartTime, EditText editTextEndTime ,EditText editTextName, EditText editNameLocation, EditText editCategories){

        saveOnDB(context, conversionEvent(editTextDate, editTextStartTime, editTextEndTime ,editTextName, editNameLocation, editCategories ));

    }

    public static Event conversionEvent (EditText editTextDate,EditText editTextStartTime, EditText editTextEndTime ,EditText editTextName, EditText editNameLocation, EditText editCategories){

        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(editTextDate.getText().toString());
        Date date = null;
        String formattedDate = null;
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formater.parse(editTextDate.getText().toString());
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Event event = new Event(formattedDate,
                editTextStartTime.getText().toString(),
                editTextEndTime.getText().toString(),
                editTextName.getText().toString(),
                editNameLocation.getText().toString(),
                editCategories.getText().toString());
        return event;
    }

    private static void saveOnDB(Context context, Event event){
        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        dataBaseHandler.addEvent(event);
    }
}
