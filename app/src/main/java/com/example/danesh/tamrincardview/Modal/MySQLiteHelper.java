package com.example.danesh.tamrincardview.Modal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class MySQLiteHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="podcast.db";
    private static final int DATABASE_VERSION=1;
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
}
