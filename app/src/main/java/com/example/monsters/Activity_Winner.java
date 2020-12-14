package com.example.monsters;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;


public class Activity_Winner extends ActivityBase {

    public static final String TOP_TEN="TOP_TEN";
    public static final String NOT_EXIST="NOT_EXIST";
    private Player winner;
    private ImageView winner_IMG_background;
    private TextView winner_LBL_title;
    private TextView winner_LBL_check;
    private ImageView winner_IMG_player;
    private Button winner_BTN_replay;
    private Button winner_BTN_save;
    private Button winner_BTN_topTen;
    private EditText winner_EDT_name;
    private TopTen topTen;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        findViews();
        initViews();
        showWinner();
    }

    private void findViews() {
        gson=new Gson();
        winner_IMG_background=findViewById(R.id.winner_IMG_background);
        winner_LBL_title=findViewById(R.id.winner_LBL_title);
        winner_LBL_check=findViewById(R.id.winner_LBL_check);
        winner_IMG_player=findViewById(R.id.winner_IMG_player);
        winner_BTN_replay=findViewById(R.id.winner_BTN_replay);
        winner_BTN_save =findViewById(R.id.winner_BTN_save);
        winner_BTN_topTen=findViewById(R.id.winner_BTN_topTen);
        winner_EDT_name=findViewById(R.id.winner_EDT_name);
    }

    private void initViews() {
        updateImage(this.getResources().getIdentifier(
                "red_curtain","drawable",this.getPackageName()),winner_IMG_background);

        initTopTen();
        initWinner();

        winner_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                winner_BTN_save.setBackgroundColor(Color.GRAY);
                winner_BTN_save.setEnabled(false);
                winner_BTN_save.setClickable(false);
                String name =winner_EDT_name.getText()+"";
                winner.setName(name);
                addAndSaveTopTen();
            }
        });

        winner_BTN_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                Intent myIntent= new Intent(Activity_Winner.this,Activity_Start.class);
                startActivity(myIntent);
                finish();
            }
        });

        winner_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.snd_bubble);
                Intent myIntent = new Intent(Activity_Winner.this,Activity_TopTen.class);
                startActivity(myIntent);
                finish();
            }
        });

    }

    private void initTopTen() {
        String gson_topTen=MySP.getInstance().getString(TOP_TEN,NOT_EXIST);
        if(gson_topTen.equals(NOT_EXIST))
            topTen = new TopTen();
        else
            topTen=gson.fromJson(gson_topTen,TopTen.class);
    }

    private void initWinner() {
        String gson_winner = getIntent().getStringExtra(MainViewController.GSON_WINNER);
        winner= gson.fromJson(gson_winner, Player.class);
        Location location = LocationFunction.getLocationOfUser(this);
        if (location != null)
            winner.setLocation(location.getLatitude(), location.getLongitude());
    }

    private void showWinner() {
        int id =getIntent().getIntExtra(MainViewController.EXTRA_KEY_WINNER,0);
        winner_IMG_player.setImageResource(id);
        if(winner.getName().equals("tie"))
            winner_LBL_title.setText("TIE!");

    }

    private void addAndSaveTopTen(){//add winner to top ten if needed and save top ten to memory
        boolean isInTopTen= topTen.checkAndAdd(winner);
        String newTopTen=gson.toJson(topTen);
        MySP.getInstance().putString(TOP_TEN,newTopTen);
        if(isInTopTen)
            winner_LBL_check.setText("Congratulations! you are in our top 10!");
        else
            winner_LBL_check.setText("Sorry.. maybe next time :)");
    }

}