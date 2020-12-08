package com.example.monsters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Activity_Winner extends ActivityBase {

    private ImageView winner_IMG_background;
    private TextView winner_LBL_title;
    private ImageView winner_IMG_player;
    private ImageButton winner_BTN_replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        findViews();
        initViews();
        showWinner();

    }

    private void findViews() {
        winner_IMG_background=findViewById(R.id.winner_IMG_background);
        winner_LBL_title=findViewById(R.id.winner_LBL_title);
        winner_IMG_player=findViewById(R.id.winner_IMG_player);
        winner_BTN_replay=findViewById(R.id.winner_BTN_replay);
    }

    private void initViews() {
        updateImage(this.getResources().getIdentifier(
                "red_curtain","drawable",this.getPackageName()),winner_IMG_background);
        updateImage(this.getResources().getIdentifier(
                "replay","drawable",this.getPackageName()),winner_BTN_replay);
        winner_BTN_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                Intent myIntent= new Intent(Activity_Winner.this,Activity_Start.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void showWinner() {
        int id =getIntent().getIntExtra(MainViewController.EXTRA_KEY_WINNER,0);
        if(id == -1)
            showTie();
        else{
            winner_IMG_player.setImageResource(id);
        }
    }

    private void showTie() {
        int id=getResources().getIdentifier("spears","drawable",this.getPackageName());
        winner_IMG_player.setImageResource(id);
        winner_LBL_title.setText("TIE!");
    }

}