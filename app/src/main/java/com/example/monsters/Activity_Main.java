package com.example.monsters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class Activity_Main extends AppCompatActivity {
    public static final String EXTRA_KEY_WINNER="EXTRA_KEY_WINNER";
    final int NUM_OF_MONSTERS=28;
    private TextView main_LBL_girl_score;
    private TextView main_LBL_boy_score;
    private int girl_score;
    private int boy_score;
    private ImageView main_IMG_girl_monster;
    private ImageView main_IMG_boy_monster;
    private ImageButton main_BTN_blades;
    ArrayList<Monster> monsters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
    }

    private void findViews() {
        main_LBL_girl_score = findViewById(R.id.main_LBL_girl_score);
        main_LBL_boy_score = findViewById(R.id.main_LBL_boy_score);
        girl_score=0;
        boy_score=0;
        main_IMG_girl_monster = findViewById(R.id.main_IMG_gitl_monster);
        main_IMG_boy_monster = findViewById(R.id.main_IMG_boy_monster);
        main_BTN_blades = findViewById(R.id.main_BTN_blades);
        initMonsters();
    }

    /**
     * in this function we connect between the image of the monster to the object id and his power
     */
    private void initMonsters() {
        monsters = new ArrayList<>();
        String name;
        int id;
        for(int i=1 ; i< NUM_OF_MONSTERS+1 ; i++){
            name = "monster_"+ (i) +"";
            id=getResources().getIdentifier(name,"drawable",this.getPackageName());
            monsters.add(new Monster(id,i));
        }
    }

    private void initViews() {
        main_BTN_blades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!monsters.isEmpty()){
                   playTurn();
                    if(monsters.isEmpty())
                        gameOver();
                }
            }
        });

    }

    private void gameOver() {
        Intent myIntent = new Intent(Activity_Main.this,Activity_Winner.class);
        if(girl_score != boy_score)
        {
            int id;
            if(girl_score>boy_score)
                id=getResources().getIdentifier("elf_girl","drawable",this.getPackageName());
            else
                id=getResources().getIdentifier("elf_boy","drawable",this.getPackageName());
            myIntent.putExtra(EXTRA_KEY_WINNER,id);
        }
        else
            myIntent.putExtra(EXTRA_KEY_WINNER,-1);
        startActivity(myIntent);
        finish();
    }

    private void playTurn() {
        int girl_monster,boy_monster;
        do{
            girl_monster = (int)(Math.random()*monsters.size());
            boy_monster = (int)(Math.random()*monsters.size());
        }while(girl_monster == boy_monster);

        //set the new monsters
        main_IMG_girl_monster.setImageResource(monsters.get(girl_monster).getId());
        main_IMG_boy_monster.setImageResource(monsters.get(boy_monster).getId());

        //set the new score power
        if(girl_monster > boy_monster)//girl wins in this turn
            main_LBL_girl_score.setText((++girl_score)+ "");
        else//boy wins in this turn
            main_LBL_boy_score.setText((++boy_score)+ "");

        if(girl_monster > boy_monster) {
            monsters.remove(girl_monster);
            monsters.remove(boy_monster);
        }
        else {
            monsters.remove(boy_monster);
            monsters.remove(girl_monster);
        }

    }


}

