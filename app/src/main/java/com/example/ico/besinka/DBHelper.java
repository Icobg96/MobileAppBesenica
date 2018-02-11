package com.example.ico.besinka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ico on 25.3.2017 г..
 */

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public static final String LOG = "dbException";

    public static final String DB_NAME = "dbGameHanging.db";
    public static final int DB_VERSION = 1;
    //Tale words
    public static final String TABLE_WORDS = "word";
    //Tale score
    public static final String TABLE_SCORE="score";
    //table words columns
    public static final String WORD_COLUMN_ID = "_id";
    public static final String WORD_COLUMN_WORD = "word";
    //table score columns
    public static final String SCORE_COLUMN_ID = "_id";
    public static final String SCORE_COLUMN_WORD = "word";
    public static final String SCORE_COLUMN_NAME = "name";
    public static final String SCORE_COLUMN_TIME = "time";
    public static final String SCORE_COLUMN_ATTEMPTS="attempts";

    public static final String CREATE_TABLE_WORDS = "CREATE TABLE " + TABLE_WORDS + "(" +
            "'" + WORD_COLUMN_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + WORD_COLUMN_WORD + "' TEXT NOT NULL UNIQUE)";

    public static final String CREATE_TABLE_SCORE="CREATE TABLE "+TABLE_SCORE+"("+
            "'"+ SCORE_COLUMN_ID+ "' INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "'"+ SCORE_COLUMN_NAME+ "' TEXT NOT NULL,"+
            "'"+SCORE_COLUMN_WORD+ "' TEXT NOT NULL,"+
            "'"+SCORE_COLUMN_TIME+ "' TEXT NOT NULL,"+
            "'"+SCORE_COLUMN_ATTEMPTS+"' INTEGER NOT NULL)";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_WORDS);
        sqLiteDatabase.execSQL(CREATE_TABLE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
               onCreate(sqLiteDatabase);

    }

    public void addWord(Word word,Context context) {

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(WORD_COLUMN_WORD, word.getWord());

            db.insertOrThrow(TABLE_WORDS, null, cv);
        }catch (SQLException e){
            Toast.makeText(context, "This word exists",
                    Toast.LENGTH_LONG).show();
            Log.e(LOG, e.getMessage());
        }finally {
            if(db != null)
                db.close();
        }
    }
    public void addScore(Score score,Context context) {

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(SCORE_COLUMN_WORD, score.getWord());
            cv.put(SCORE_COLUMN_NAME, score.getName());
            cv.put(SCORE_COLUMN_TIME, score.getTaim());
            cv.put(SCORE_COLUMN_ATTEMPTS, score.getAttempts());

            db.insertOrThrow(TABLE_SCORE, null, cv);
        }catch (SQLException e){

            Log.e(LOG, e.getMessage());
        }finally {
            if(db != null)
                db.close();
        }
    }
    public ArrayList<String> getAllWords(){
        ArrayList<String> list =new ArrayList<String>();
        Cursor c = null;

      try {
          String query = "SELECT * FROM " + TABLE_WORDS;

          db =getReadableDatabase();
          c = db.rawQuery(query, null);
          if (c.moveToFirst()) {
              do {

                  list.add(c.getString(c.getColumnIndex(WORD_COLUMN_WORD)));


              } while (c.moveToNext());
          }
      }catch (SQLException e){
            Log.e(LOG, e.getMessage());
        }finally {
            if(c != null)
                c.close();
            if(db != null)
                db.close();
        }
        return list;
    }
    public void deleteWord(String word){
        try {
            db =getWritableDatabase();
        db.execSQL("delete from "+TABLE_WORDS+" where word ='"+word+"'");
        }catch (SQLException e){
            Log.e(LOG, e.getMessage());
        }finally {
            if (db != null)
                db.close();
        }
    }
    public void updateWord(String word,String newWord,Context context) {
        try {
            db = getWritableDatabase();

            db.execSQL("update " + TABLE_WORDS + " set word ='" + newWord + "' where word ='" + word + "'");
        } catch (SQLException e) {
            Toast.makeText(context, "This word exists",
                    Toast.LENGTH_LONG).show();
            Log.e(LOG, e.getMessage());
        } finally {

            if (db != null)
                db.close();
        }
    }
    public String randomGenerateWord(){
        String word="";
        Cursor c = null;

        try {
            String query = "SELECT * FROM " + TABLE_WORDS+" ORDER BY RANDOM() LIMIT "+1;

            db =getReadableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {

                    word=(c.getString(c.getColumnIndex(WORD_COLUMN_WORD)));


                } while (c.moveToNext());
            }
        }catch (SQLException e){
            Log.e(LOG, e.getMessage());
        }finally {
            if(c != null)
                c.close();
            if(db != null)
                db.close();
        }
        return word;
    }
    public ArrayList<String> getAllScor(){
        ArrayList<String> list =new ArrayList<String>();
        Cursor c = null;

        try {
            String query = "SELECT * FROM " + TABLE_SCORE;

            db =getReadableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {

                    list.add("Name: "+c.getString(c.getColumnIndex(SCORE_COLUMN_NAME))+"/Word: "
                    +c.getString(c.getColumnIndex(SCORE_COLUMN_WORD))+"/Attempts: "
                    +c.getString(c.getColumnIndex(SCORE_COLUMN_ATTEMPTS))+"/Time: "
                    +c.getString(c.getColumnIndex(SCORE_COLUMN_TIME)));


                } while (c.moveToNext());
            }
        }catch (SQLException e){
            Log.e(LOG, e.getMessage());
        }finally {
            if(c != null)
                c.close();
            if(db != null)
                db.close();
        }
        return list;
    }
    public void deleteAllScore(){

       try {
           db =getWritableDatabase();
           db.delete(TABLE_SCORE,null,null);
       }catch (SQLException e){
           Log.e(LOG, e.getMessage());
       }finally {
           db.close();
       }

    }

}
