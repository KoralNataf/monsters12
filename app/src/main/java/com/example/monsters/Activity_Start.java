package com.example.monsters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Activity_Start extends ActivityBase {
    private ImageView start_IMG_background;
    private Button start_BTN_play;
    private Button start_BTN_top_10;
    private static final String TAG="Activity_Start";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        initViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationFunction.askLocationPermission(this);
    }

    private void initViews() {
        updateImage(getResources().getIdentifier(
                "forest", "drawable", getPackageName()), start_IMG_background);
        start_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                Intent myIntent = new Intent(Activity_Start.this,Activity_Main.class);
                startActivity(myIntent);
                finish();
            }
        });

        start_BTN_top_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                Intent myIntent = new Intent(Activity_Start.this,Activity_TopTen.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void findViews() {
        start_IMG_background=findViewById(R.id.start_IMG_background);
        start_BTN_play=findViewById(R.id.start_BTN_play);
        start_BTN_top_10=findViewById(R.id.start_BTN_top_10);
    }
}