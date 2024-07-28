package com.example.pocketdiaryapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeDataPicker {

    private static int mYear, mMonth, mDay, mHour, mMinute;
    private static SimpleDateFormat dfhour = new SimpleDateFormat("HH");
    private static SimpleDateFormat dfminute = new SimpleDateFormat("mm");

    public static void callTimePicker(Context context, EditText editTextTime) {
        // получаем текущее время
        final Calendar cal = Calendar.getInstance();
        mHour = Integer.parseInt(dfhour.format(cal.getTime()));
        mMinute = Integer.parseInt(dfminute.format(cal.getTime()));
        //Log.d("mysrcl: ", Integer.toString(mHour) + ":" + Integer.toString(mMinute));
        // инициализируем диалог выбора времени текущими значениями
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String editTextTimeParam = String.format("%02d" ,hourOfDay) + ":" + String.format("%02d" ,minute);
                        editTextTime.setText(editTextTimeParam);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public static void callDatePicker(Context context, EditText editTextDate) {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        editTextDate.setText(editTextDateParam);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
