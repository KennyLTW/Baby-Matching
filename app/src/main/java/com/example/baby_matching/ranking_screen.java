package com.example.baby_matching;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ranking_screen extends AppCompatActivity {

    String sql = "select * from player_info ORDER BY score DESC LIMIT 10";

    final String DATABASE_NAME = "game_record.db";

    ListView name_list, diff_list, time_list, score_list;

    String name;
    String diff;
    String u_time;
    String score;

    String[] Arr_name;
    String[] Arr_diff;
    String[] Arr_u_time;
    String[] Arr_score;

    ArrayList <String> AL_name = new ArrayList<String>();
    ArrayList <String> AL_diff = new ArrayList<String>();
    ArrayList <String> AL_time = new ArrayList<String>();
    ArrayList <String> AL_score = new ArrayList<String>();

    Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_screen);

        name_list = (ListView) findViewById(R.id.name_list);
        diff_list = (ListView) findViewById(R.id.diff_list);
        time_list = (ListView) findViewById(R.id.usedtime_list);
        score_list = (ListView) findViewById(R.id.score_list);

        quit = (Button)findViewById(R.id.quit);

        final SQLiteDatabase db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery(sql,null);


        int k = 0;

        if(cursor.getCount() > 0) {
            int total_record = cursor.getCount();
            Arr_name = new String[total_record];
            Arr_diff = new String[total_record];
            Arr_score = new String[total_record];
            Arr_u_time = new String[total_record];
            cursor.moveToFirst();
            do {
                name = cursor.getString(0);
                Arr_name[k] = name;
                diff = cursor.getString(1);
                Arr_diff[k] = diff;
                score = String.valueOf(cursor.getInt(2));
                Arr_score[k] = score;
                u_time = String.valueOf(cursor.getInt(3));
                Arr_u_time[k] = u_time;
                Log.i("SQL", "" + name + "," + diff + "," + score + "," + u_time);
                k++;
            } while (cursor.moveToNext());

        }

        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item);
        arrayAdapter.addAll(Arr_name);
        ArrayAdapter <String> arrayAdapter1 = new ArrayAdapter<String>(this,R.layout.list_item);
        arrayAdapter1.addAll(Arr_diff);
        ArrayAdapter <String> arrayAdapter2 = new ArrayAdapter<String>(this,R.layout.list_item);
        arrayAdapter2.addAll(Arr_score);
        ArrayAdapter <String> arrayAdapter3 = new ArrayAdapter<String>(this,R.layout.list_item);
        arrayAdapter3.addAll(Arr_u_time);

        name_list.setAdapter(arrayAdapter);
        diff_list.setAdapter(arrayAdapter1);
        score_list.setAdapter(arrayAdapter2);
        time_list.setAdapter(arrayAdapter3);


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(ranking_screen.this, MainActivity.class);
                startActivity(intent6);
            }
        });

    }

}
