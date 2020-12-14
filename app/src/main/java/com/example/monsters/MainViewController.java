package com.example.monsters;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewController {

    private ActivityBase activity;
    private Timer carousalTimer;
    final int DELAY=1400; //1400ms = 1.4 sec
    private ProgressBar main_PB;
    private int progress = 0 ;
    private ImageView main_IMG_background;
    public static final String EXTRA_KEY_WINNER="EXTRA_KEY_WINNER";
    public static final String GSON_WINNER="GSON_WINNER";
    private final int NUM_OF_MONSTERS=28;
    private TextView main_LBL_girl_score;
    private TextView main_LBL_boy_score;
    private int girl_score;
    private int boy_score;
    private ImageView main_IMG_girl_monster;
    private ImageView main_IMG_boy_monster;
    private ImageButton main_BTN_blades;
    private boolean isClicked_blades=false;
    ArrayList<Monster> monsters;
    Gson gson;

   public MainViewController(ActivityBase activity){
       this.activity=activity;
       findViews();
       initViews();
   }

    private void findViews() {
        gson= new Gson();
        main_PB=activity.findViewById(R.id.main_PB);
        main_IMG_background = activity.findViewById(R.id.main_IMG_background);
        main_LBL_girl_score = activity.findViewById(R.id.main_LBL_girl_score);
        main_LBL_boy_score = activity.findViewById(R.id.main_LBL_boy_score);
        girl_score=0;
        boy_score=0;
        main_IMG_girl_monster = activity.findViewById(R.id.main_IMG_gitl_monster);
        main_IMG_boy_monster = activity.findViewById(R.id.main_IMG_boy_monster);
        main_BTN_blades = activity.findViewById(R.id.main_BTN_blades);
        initMonsters();
    }

    private void initViews() {
        activity.updateImage(activity.getResources().getIdentifier(
                "forest","drawable",activity.getPackageName()),main_IMG_background);
        activity.updateImage(activity.getResources().getIdentifier(
                "blades","drawable",activity.getPackageName()),main_BTN_blades);
        main_BTN_blades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.playSound(R.raw.snd_bubble);
                isClicked_blades=true;
                main_BTN_blades.setEnabled(false);
                main_BTN_blades.setClickable(false);
                startTimer();
            }
        });

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
            id=activity.getResources().getIdentifier(name,"drawable",activity.getPackageName());
            monsters.add(new Monster(id,i));
        }
    }

    protected void startTimer() {
        carousalTimer = new Timer();
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!monsters.isEmpty()){
                            activity.playSound(R.raw.snd_monster_switch);
                            playTurn();
                            if(monsters.isEmpty())
                                gameOver();
                        }
                    }
                });
            }
        }, 0, DELAY);
    }

    protected void stopTimer() {
        carousalTimer.cancel();
    }

    private void playTurn() {
        int girl_monster, boy_monster;
        main_PB.setProgress(++progress);
        do {
            girl_monster = (int) (Math.random() * monsters.size());
            boy_monster = (int) (Math.random() * monsters.size());
        } while (girl_monster == boy_monster);


        //set the new monsters
        activity.updateImage(monsters.get(girl_monster).getId() ,main_IMG_girl_monster);
        activity.updateImage(monsters.get(boy_monster).getId() ,main_IMG_boy_monster);

        //set the new score power
        if (girl_monster > boy_monster)//girl wins in this turn
            main_LBL_girl_score.setText((++girl_score) + "");
        else//boy wins in this turn
            main_LBL_boy_score.setText((++boy_score) + "");

        if (girl_monster > boy_monster) {
            monsters.remove(girl_monster);
            monsters.remove(boy_monster);
        } else {
            monsters.remove(boy_monster);
            monsters.remove(girl_monster);
        }
    }

    private void gameOver() {
        Intent myIntent = new Intent(activity,Activity_Winner.class);
            int id,score;
            String name;

            if(girl_score>boy_score) {
                id = activity.getResources().getIdentifier("elf_girl", "drawable", activity.getPackageName());
                name = "elf girl";
                score = girl_score;
            }
            else if(girl_score<boy_score) {
                id=activity.getResources().getIdentifier("elf_boy","drawable",activity.getPackageName());
                name="elf boy";
                score= boy_score;
            }else {
                name = "tie";
                score = boy_score;
                id=activity.getResources().getIdentifier("spears","drawable",activity.getPackageName());
            }

            Player winner = new Player(name,score);
            String gson_winner = gson.toJson(winner);
            myIntent.putExtra(EXTRA_KEY_WINNER,id);
            myIntent.putExtra(GSON_WINNER,gson_winner);

        activity.startActivity(myIntent);
        activity.finish();
    }

    public boolean isClicked() {
        return isClicked_blades;
    }
}
