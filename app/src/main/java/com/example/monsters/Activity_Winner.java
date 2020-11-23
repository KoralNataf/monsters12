package com.example.monsters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Winner extends AppCompatActivity {

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
        winner_LBL_title=findViewById(R.id.winner_LBL_title);
        winner_IMG_player=findViewById(R.id.winner_IMG_player);
        winner_BTN_replay=findViewById(R.id.winner_BTN_replay);
    }

    private void initViews() {
        winner_BTN_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent= new Intent(Activity_Winner.this,Activity_Main.class);
                startActivity(secondIntent);
                finish();
            }
        });
    }

    private void showWinner() {
        int id =getIntent().getIntExtra(Activity_Main.EXTRA_KEY_WINNER,0);
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