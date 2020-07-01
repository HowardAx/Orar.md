package com.app.orarmd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBaccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DBaccess instance;
    Cursor c = null;

    private DBaccess(Context context){
        this.openHelper = new DBopenHelper(context);
    }

    public static DBaccess getInstance(Context context){
        if(instance==null){
            instance = new DBaccess(context);
        }
        return instance;
    }

    public void open(){
        this.sqLiteDatabase = openHelper.getReadableDatabase();
    }

    public void close(){
        if(sqLiteDatabase!=null){
            this.sqLiteDatabase.close();
        }
    }

    public String getCloserTransport(String currTime, String transport, String tabel) {
        c = sqLiteDatabase.rawQuery("SELECT " + transport + " FROM " + tabel +
                " WHERE " + transport + " > " + currTime +
                " ORDER BY " + transport + " ASC LIMIT 2", new String[]{});
        StringBuffer buffer = new StringBuffer();

        int z=0;
        while (c.moveToNext()) {

            String ora = c.getString(0);
            if(z==0){
                buffer.append("" + ora);
                z++;
            } else {
                buffer.append(":" + ora);
            }

        }
        return buffer.toString();

    }

}