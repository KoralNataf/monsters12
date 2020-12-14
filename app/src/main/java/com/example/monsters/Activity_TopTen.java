package com.example.monsters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_TopTen extends ActivityBase {
    private Button top_ten_BTN_back;
    private  Fragment_List fragment_list;
    private Fragment_Map fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        findViews();
        initViews();
    }

    private void findViews() {
        top_ten_BTN_back=findViewById(R.id.top_ten_BTN_back);
    }

    private void initViews() {
        fragment_list= new Fragment_List();
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_FRG_list,fragment_list).commit();

        fragment_map=new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_FRG_map,fragment_map).commit();

        fragment_list.setCallBack(callBack);

        top_ten_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                Intent myIntent = new Intent(Activity_TopTen.this,Activity_Start.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    private CallBack callBack = new CallBack() {

        @Override
        public void updateMapLocation(double lat , double lon) {
            fragment_map.setLatLong(lat,lon);
        }
    };
}