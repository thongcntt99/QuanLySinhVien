package com.example.homework4_6_2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.homework4_6_2.iteamModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCreate extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASENAME = "Student.db";
    public static final int VERSION = 1;
    public static final String COMA_SEP = ",";

    //column of meeting table
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_ID = "id_student";
    public static final String COLUMN_MSSV = "mssv";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BIRTHPLACE= "birth_place";
    public static final String COLUMN_DOB= "dob";
    public static final String COLUMN_FLAG= "flag";

    public static final String SQLITE_CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY " + "AUTOINCREMENT " + COMA_SEP +
        COLUMN_MSSV + " TEXT" + COMA_SEP +
        COLUMN_FULL_NAME + " TEXT" + COMA_SEP +
        COLUMN_EMAIL + " TEXT" + COMA_SEP +
        COLUMN_BIRTHPLACE + " TEXT"+ COMA_SEP+
        COLUMN_DOB + " TEXT " + ")";

    public DatabaseCreate(@Nullable Context context) {
        super(context, DATABASENAME,null,VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLITE_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public void addStudent(iteamModel student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MSSV, student.getMssv());
        values.put(COLUMN_FULL_NAME, student.getFullname());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_BIRTHPLACE, student.getBirthplace());
        values.put(COLUMN_DOB, student.getDob());

        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }
    public List<iteamModel> getAllStudents() {
        List<iteamModel>  studentList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_STUDENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            iteamModel student = new iteamModel(cursor.getInt(0),cursor.getString(2), cursor.getString(1), cursor.getString(5), cursor.getString(3), cursor.getString(4));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }
    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, COLUMN_ID + " = ?", new String[] { String.valueOf(studentId) });
        db.close();
    }
    public boolean checkStudent(String mssv) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENT, null, COLUMN_MSSV + " = ?", new String[] {mssv},null, null, null);
        if(cursor.getCount() == 0) {
            return true;
        }
        return false;
    }
    public void updateStudent(iteamModel student,int id_student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MSSV, student.getMssv());
        values.put(COLUMN_FULL_NAME, student.getFullname());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_DOB, student.getDob());
        values.put(COLUMN_BIRTHPLACE, student.getBirthplace());

        db.update(TABLE_STUDENT, values, COLUMN_ID + " = ?", new String[] { String.valueOf(id_student) });
        db.close();
    }
}
