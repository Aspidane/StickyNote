package com.aspivina.stickynote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tyler on 3/8/2018.
 */

public class StickyNoteDB extends SQLiteOpenHelper {

    //Variables for the database's name and version number
    private static String database_name="StickyNoteDB";
    private static int database_version=1;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public StickyNoteDB(Context context){
        super(context, database_name, null, database_version);

    }
}
