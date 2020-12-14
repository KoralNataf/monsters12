package com.example.monsters;

import android.os.Bundle;

public class Activity_Main extends ActivityBase {

    private MainViewController main_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_controller=new MainViewController(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(main_controller.isClicked())
            main_controller.startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        main_controller.stopTimer();
    }
}

