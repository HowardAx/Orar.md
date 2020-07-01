package com.app.orarmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBopenHelper extends SQLiteAssetHelper {

    private final static String DB_NAME = "orartransportmd.db";
    private final static int DB_VERSION = 1;


    public DBopenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
