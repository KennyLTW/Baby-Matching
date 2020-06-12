package com.example.baby_matching;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.*;
import java.lang.*;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.os.Vibrator;

import javax.xml.transform.Result;

public class play_game extends AppCompatActivity {

    private GridView grid;
    private ArrayAdapter<String> adapter;

    ImageView img_view =  null;     //1
    ImageView img_view2 =  null;    //2

    //Timer
    TextView timer;
    long gametimer;

    //Score
    TextView score;
    int s = 0;

    private int conuntPair = 8;
    final int[] drawable = new int[] {
            R.drawable.apple, R.drawable.banana, R.drawable.car, R.drawable.dog, R.drawable.flower, R.drawable.flower_1, R.drawable.orange, R.drawable.taxi
    };

    int[] pos = {0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7};
    Random random = new Random();

    int currPos = -1;   //first click
    int nextPos = -2;   //sec click

    boolean currSelected = false;
    boolean nextSelected = false;

    public int currImageID = -999;
    public int nextImageID = -998;

    String difficulty = "normal";
    long selectedtime;

    public CountDownTimer a;
    public PlayBackgroundSound playBackgroundSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        //Sound
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.correct);

        //Vibrator
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        gamestart();

        //todo: start game
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //todo: select the first image
                if(currPos < 0 && !currSelected) {
                    currPos = position;
                    currSelected = true;
                    img_view = (ImageView) view;
                    img_view.setImageResource(drawable[pos[currPos]]);
                    currImageID = pos[currPos];
                }
                //todo: select the second image
                else if(nextPos < 0 && !nextSelected && (currPos!=position) && currSelected) {
                    nextPos = position;
                    nextSelected = true;
                    img_view2 = (ImageView) view;
                    img_view2.setImageResource(drawable[pos[nextPos]]);
                    nextImageID = pos[nextPos];
                }

                //delay to remove pict
                if (currSelected && nextSelected) {
                    if (currImageID == nextImageID) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Match!",Toast.LENGTH_SHORT).show();
                                reset();
                                s+=100;

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mediaPlayer.setVolume(1.0f,1.0f);
                                        mediaPlayer.start();
                                    }
                                }).start();

                                //mediaPlayer.setVolume(1.0f,1.0f);
                                //mediaPlayer.start();
                                score.setText("Score:"+s);
                                img_view.setVisibility(View.GONE);
                                img_view2.setVisibility(View.GONE);
                                conuntPair--;
                                if(conuntPair == 0) {
                                    Toast.makeText(getApplicationContext(),"End!",Toast.LENGTH_SHORT).show();
                                    a.cancel();
                                    end();
                                }
                            }
                        },500);
                    } else {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Not Match!",Toast.LENGTH_SHORT).show();
                                v.vibrate(500);
                                if(s>0) {
                                    s-=10;
                                    score.setText("Score:"+s);
                                }
                                reset();
                                img_view.setImageResource(R.drawable.back);
                                img_view2.setImageResource(R.drawable.back);
                            }
                        },500);
                    }
                }
            }

        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)) {
            end();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public class PlayBackgroundSound extends AsyncTask<Void, Void, Void> {

        MediaPlayer bg_music = MediaPlayer.create(play_game.this,R.raw.bgmusic);

        @Override
        protected Void doInBackground(Void...params) {
            bg_music.setLooping(true);
            bg_music.setVolume(0.2f,0.2f);
            bg_music.start();
            return null;
        }

        @Override
        protected void onCancelled() {
            bg_music.stop();
        }
    }

    public void reset() {
        currSelected = false;
        nextSelected = false;
        currImageID = -999;
        nextImageID = -998;
        currPos = -1;
        nextPos = -2;
    }

    public void end(){
        Intent intent3 = new Intent(play_game.this, end_game.class);
        intent3.putExtra("selectedtime", selectedtime);
        intent3.putExtra("gametime", gametimer);
        intent3.putExtra("score", s);
        intent3.putExtra("difficulty", difficulty);
        playBackgroundSound.onCancelled();
        startActivity(intent3);
    }

    public void gamestart(){

        playBackgroundSound = new PlayBackgroundSound(); //play bg music
        playBackgroundSound.execute();

        if(difficulty == "easy"){
            selectedtime = 90000;
            gametimer = 91000;          //90s
        }else if(difficulty == "normal"){
            selectedtime = 60000;
            gametimer = 61000;          //60s
        }else if(difficulty == "hard"){
            selectedtime = 30000;
            gametimer = 31000;          //30s
        }else{

        }

        for (int i = 0; i < pos.length; i++) {
            int randpos = random.nextInt(pos.length);
            int temp = pos[i];
            pos[i] = pos[randpos];
            pos[randpos] = temp;
        }

        timer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);

        //init
        score.setText("Score:0");

        grid = (GridView) findViewById(R.id.grid);
        Layout_Adapter img_adapt = new Layout_Adapter(this);
        grid.setAdapter(img_adapt);

        //todo: countdown
        a =new CountDownTimer(gametimer, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("Time:"+millisUntilFinished/1000);
                gametimer = millisUntilFinished;

            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Time's Up",Toast.LENGTH_SHORT).show();
                end();
            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent4 = new Intent(play_game.this, MainActivity.class);

        switch (item.getItemId()) {
            case R.id.restart:
                a.cancel();
                playBackgroundSound.onCancelled();
                s = 0;
                gamestart();
                break;
            case R.id.easy:
                item.setChecked(true);
                a.cancel();
                playBackgroundSound.onCancelled();
                s = 0;
                difficulty = "easy";
                gamestart();
                break;
            case R.id.normal:
                item.setChecked(true);
                a.cancel();
                playBackgroundSound.onCancelled();
                s = 0;
                difficulty = "normal";
                gamestart();
                break;
            case R.id.hard:
                item.setChecked(true);
                a.cancel();
                playBackgroundSound.onCancelled();
                s = 0;
                difficulty = "hard";
                gamestart();
                break;
            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle("About Baby Matching")
                        .setMessage("How to play:\nMatching the same pictures in pairs!\nMatch: +100 score\nNot Match: -10 score")
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
                break;
            case R.id.quit:
                playBackgroundSound.onCancelled();
                a.cancel();;
                startActivity(intent4);
                break;
        }

        return false;
    }


}

