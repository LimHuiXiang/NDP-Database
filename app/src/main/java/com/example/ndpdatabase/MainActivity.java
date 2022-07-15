package com.example.ndpdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert,btnShowList;
    RadioButton r1,r2,r3,r4,r5;
    EditText e1,e2,e3;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.buttonInsert);
        btnShowList = findViewById(R.id.buttonList);
        r1 = findViewById(R.id.radioButton1);
        r2 = findViewById(R.id.radioButton2);
        r3 = findViewById(R.id.radioButton3);
        r4 = findViewById(R.id.radioButton4);
        r5 = findViewById(R.id.radioButton5);
        e1 = findViewById(R.id.editTextSong);
        e2 = findViewById(R.id.editTextSingers);
        e3 = findViewById(R.id.editTextYear);

        al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1,al);



        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = e1.getText().toString();
                String data2 = e2.getText().toString();
                String data3 = e3.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);
                if (inserted_id != -1){
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
          }
        });


    }
}