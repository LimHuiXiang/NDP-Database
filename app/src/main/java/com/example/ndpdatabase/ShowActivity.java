package com.example.ndpdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowActivity  extends AppCompatActivity {
    Button BtnShow;
    ListView lv;
    Song data;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        BtnShow = findViewById(R.id.buttonShow);
        lv = findViewById(R.id.ListViewSong);
        al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");


        BtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                al.clear();
                al.addAll(dbh.getAllSongs(5));
                aa.notifyDataSetChanged();
            }
        });


    }
}
