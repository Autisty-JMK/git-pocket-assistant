package com.example.pocketdiaryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Utils.Controller;

public class Fragment_appeal extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_appeal, container, false);
        Controller controller = new Controller();
        controller.start();
        return rootView;
    }
}