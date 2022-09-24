package com.example.emprecords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {//main class for handling sqlite sqlitehelper
    public static final String DATABASE_NAME = "employee.db";
    public static final String TABLE_NAME = "employee_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "GENDER";
    public static final String COL_4 = "SALARY";

    public DatabaseHelper(Context context) {//function to create db//whenever this is call db will created//it is an constructor
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//to create table inside db
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR,GENDER VARCHAR ,SALARY INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);//creating obj sql
        onCreate(db);//creating table

    }

    public boolean insertData(String name,String gender,String salary) {
        SQLiteDatabase db = this.getWritableDatabase();//use this instance to create
        ContentValues contentValues = new ContentValues();//create intance
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,gender);
        contentValues.put(COL_4,salary);
        long result = db.insert(TABLE_NAME,null ,contentValues);//to know data is inserted
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {//function to see data records
        SQLiteDatabase db = this.getWritableDatabase();//instance
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);//creating query
        return res;
    }

    public boolean updateData(String id,String name,String gender,String salary) {
        SQLiteDatabase db = this.getWritableDatabase();//instance
        ContentValues contentValues = new ContentValues();//instance
        contentValues.put(COL_1,id);//putting data
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,gender);
        contentValues.put(COL_4,salary);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });//different method ie update//update based on id because it i
        return true;                                                                 //is primary key//4th argument is string array
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}