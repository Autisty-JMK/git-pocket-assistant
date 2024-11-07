package com.example.pocketdiaryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import Data.Controller;
import Model.EventAPI;
import retrofit2.Call;

public class Fragment_appeal extends Fragment implements View.OnClickListener{

    Button getAllBtn, genOneBtn, addBtn, updtBtn, dellBtn;
    EditText tx_result;
    Controller controller;
    EventAPI eventAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_appeal, container, false);

        getAllBtn  = rootView.findViewById(R.id.btd_api_get_all);
        genOneBtn = rootView.findViewById(R.id.btn_api_get_one);
        addBtn = rootView.findViewById(R.id.btn_api_add);
        updtBtn = rootView.findViewById(R.id.btn_api_updt);
        dellBtn = rootView.findViewById(R.id.btn_api_del) ;

        getAllBtn.setOnClickListener(this::onClick);
        genOneBtn.setOnClickListener(this::onClick);
        addBtn.setOnClickListener(this::onClick);
        updtBtn.setOnClickListener(this::onClick);
        dellBtn.setOnClickListener(this::onClick);

        tx_result = rootView.findViewById(R.id.tx_api_resulp);
        controller = new Controller(tx_result);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        controller.start(id);

    }

    private EventAPI getEventOnTxResult(){
        //eventAPI = new EventAPI();
        return eventAPI;
    };
}