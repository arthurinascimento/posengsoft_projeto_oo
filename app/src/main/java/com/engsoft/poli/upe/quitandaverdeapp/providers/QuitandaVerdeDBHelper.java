package com.engsoft.poli.upe.quitandaverdeapp.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuitandaVerdeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quitandaverde.db";
    private static final int DATABASE_VERSION = 1;

    public QuitandaVerdeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        QuitandaVerdeDBCreator.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        QuitandaVerdeDBCreator.onUpgrade(database, oldVersion, newVersion);
    }


}
