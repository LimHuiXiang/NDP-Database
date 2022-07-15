package com.example.ndpdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

public class DBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "simplesongs.db";
        private static final int DATABASE_VERSION = 2;
        private static final String TABLE_SONG = "song";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_TITLE = "title";
        private static final String COLUMN_SINGERS = "singers";
        private static final String COLUMN_YEAR= "year";
        private static final String COLUMN_STARS = "stars";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT," + COLUMN_SINGERS  +"TEXT ," +
                    COLUMN_YEAR +"TEXT," + COLUMN_STARS + "TEXT) ";
            db.execSQL(createSongTableSql);
            Log.i("info", "created tables");

            //Dummy records, to be inserted when the database is created
            for (int i = 0; i< 2; i++) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_TITLE, "Song number " + i);
                db.insert(TABLE_SONG, null, values);
            }
            Log.i("info", "dummy records inserted");
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN  module_name TEXT ");
        }

        public long insertNote(String noteContent) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, noteContent);
            long result = db.insert(TABLE_SONG, null, values);
            db.close();
            Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
            return result;
        }

    public ArrayList<Song> getAllSongs(int starRating) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_STARS + " = ?";
        String[] args = { String.valueOf(starRating) };
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

        public int updateNote(Song data){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, data.getTitle());
            values.put(COLUMN_SINGERS, data.getSingers());
            values.put(COLUMN_YEAR, data.getYear());
            values.put(COLUMN_STARS, data.getStars());
            String condition = COLUMN_ID + "= ?";
            String[] args = {String.valueOf(data.getId())};
            int result = db.update(TABLE_SONG, values, condition, args);
            db.close();
            return result;
        }

        public ArrayList<Song> getAllSongs(String keyword) {
            ArrayList<Song> songs = new ArrayList<Song>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
            String condition = COLUMN_TITLE + " Like ?";
            String[] args = { "%" +  keyword + "%"};
            Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                    null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String singers = cursor.getString(2);
                    int year = cursor.getInt(3);
                    int stars = cursor.getInt(4);

                    Song song = new Song(id, title,singers,year,stars);
                    songs.add(song);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return songs;
        }


}

