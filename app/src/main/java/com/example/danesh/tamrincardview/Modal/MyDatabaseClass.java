package com.example.danesh.tamrincardview.Modal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseClass {
    private static MyDatabaseClass INSTANCE;
    private static Context context;
    private static SQLiteDatabase database;
    private static MySQLiteHelper myhelper;

    public MyDatabaseClass() {
        myhelper = new MySQLiteHelper(context);
    }

    public static MyDatabaseClass getInstance(Context context) {
        MyDatabaseClass.context = context;
        if (INSTANCE == null) {
            INSTANCE = new MyDatabaseClass();
        }
        return INSTANCE;
    }

    public List<LessonClass> getAllLessonData() {
        List<LessonClass> lessonList = new ArrayList<LessonClass>();
        openDB();
        String sql ="SELECT * FROM Lesson_table";

        Cursor cr = database.rawQuery(sql, null);
        LessonClass lesson;
        if (cr != null && cr.getCount() != 0) {
            Toast.makeText(context,cr.getColumnCount()+"  columns ",Toast.LENGTH_SHORT).show();
            while (cr.moveToNext()) {
                lesson = new LessonClass();
                lesson.setNameLesson(cr.getString(cr.getColumnIndex("name_lesson")));

                lesson.setTaglineLesson(cr.getString(cr.getColumnIndex("tagline_lesson")));
                lesson.setPicLesson(cr.getBlob(cr.getColumnIndex("pic_lesson")));
                lessonList.add(lesson);


            }
        }

        closDB();
        return lessonList;
    }

    private void closDB() {
        database.close();
    }

    private void openDB() throws SQLiteException {
        database = myhelper.getWritableDatabase();
    }
}
