package com.example.latihanp11.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.latihanp11.model.Person;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "data_person";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE data_person (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT NOT NULL, " +
                "alamat TEXT NOT NULL," +
                "noTelp TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS data_person");
        onCreate(db);

    }

    public void insert(String nama, String alamat, String noTelp) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryValues = "INSERT INTO data_person(nama, alamat, noTelp)" +
                " VALUES ('"+nama+"','"+alamat+"','"+noTelp+"')";
        db.execSQL(queryValues);
        db.close();
    }

    public ArrayList<Person> getAllData(){
        ArrayList<Person> person = new ArrayList<>();
        String selectQuery = "SELECT * FROM data_person";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);
            String alamat = cursor.getString(2);
            String noTelp = cursor.getString(3);
            person.add(new Person(id, nama, alamat, noTelp));
        }
        db.close();
        return person;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String deleteQuery = "DELETE FROM data_person WHERE id = "+id;
        db.execSQL(deleteQuery);
        db.close();
    }

}
