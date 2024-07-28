package com.example.pocketdiaryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Adapter.RVadapterList;

public class AddEvent extends AppCompatActivity implements View.OnClickListener{

    private RVadapterList rv_adapterList;
    private EditText editTextDate, editTextTime, editTextName;
    private Button btnClose, btnSave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_events);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDate = (EditText) findViewById(R.id.editDatatext);
        editTextTime = (EditText) findViewById(R.id.editStartTimetext);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnSave = (Button) findViewById(R.id.btnSave);


        editTextDate.setOnClickListener(this);
        editTextTime.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.editDatatext:
                // вызываем диалог с выбором даты
                TimeDataPicker.callDatePicker(this, editTextDate);
                break;
            case R.id.editStartTimetext:
                // вызываем диалог с выбором времени
                TimeDataPicker.callTimePicker(this,editTextTime);
                break;
            case R.id.btnClose:
                // закрываем окно
                this.finish();
                break;
            case R.id.btnSave:
                // вызываем сохранение
                //EventSaveFunctions.save(this, editTextDate, editTextTime, editTextName);
                break;

        }
    }

}
