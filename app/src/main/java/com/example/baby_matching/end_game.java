package com.example.baby_matching;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.database.sqlite.*;

public class end_game extends AppCompatActivity {

    TextView scoretext;
    TextView timetext;
    TextView difficultytext;
    EditText typedname;
    Button submit;
    Button quit;
    long selectedtime;
    long gametime;
    long usedtime;
    int score;
    String difficulty;
    public String name;


    final String DATABASE_NAME = "game_record.db";
    final String TABLE_NAME = "player_info";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        selectedtime = intent.getLongExtra("selectedtime", 0);
        gametime = intent.getLongExtra("gametime", 0);
        score = intent.getIntExtra("score", 0);
        difficulty = intent.getStringExtra("difficulty");
        usedtime = ((selectedtime - gametime)/1000) + 1;

        scoretext = (TextView)findViewById(R.id.scoretext);
        timetext = (TextView)findViewById(R.id.usedtime);
        difficultytext = (TextView)findViewById(R.id.difficultytext);
        typedname = (EditText)findViewById(R.id.typedname);
        submit = (Button)findViewById(R.id.submit);
        quit = (Button)findViewById(R.id.quit);

        difficultytext.setText("Difficulty: " + difficulty);
        scoretext.setText("Score: " + score);
        timetext.setText("Used Time: " + usedtime);

        final SQLiteDatabase db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);


        //create db
        String createTable="CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME +
                "(name VARCHAR(32), " +
                "difficulty VARCHAR(16), " +
                "score int(64), "+
                "used_time int(10));";
        db.execSQL(createTable);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                name = typedname.getText().toString();

                String sql = "INSERT INTO " + TABLE_NAME + " (name, difficulty, score, used_time) VALUES ('"+
                        name+"', '"+difficulty+"', "+score+", "+usedtime+");";
                db.execSQL(sql);

                AlertDialog.Builder message = new AlertDialog.Builder(end_game.this);
                message.setTitle("Save Result");
                message.setMessage("Name: " + name + "\n" + "Difficulty: " + difficulty + "\n" + "Score: " + score + "\n" + "Used Time: " + usedtime);
                message.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent5 = new Intent(end_game.this, MainActivity.class);
                        startActivity(intent5);
                    }
                });
                message.show();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(end_game.this, MainActivity.class);
                startActivity(intent6);
            }
        });
    }

}
