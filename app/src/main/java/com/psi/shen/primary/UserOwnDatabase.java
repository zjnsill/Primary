package com.psi.shen.primary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserOwnDatabase extends SQLiteOpenHelper {
    private String tableName;
    public UserOwnDatabase(Context context, String tableName, SQLiteDatabase.CursorFactory factory,int version){
        super(context,"AllUserData.db",factory,version);
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String sql = "create table "+tableName+" (_id integer primary key autoincrement,name varchar(20),component varchar(20)," +
                "density decimal(6,2),thermalExpan decimal(6,2),thermalCon decimal(6,2),specificHeat decimal(6,2),resistivity decimal(6,2),"+
                "elasticModu decimal(6,2),meltingRange_min decimal(6,2),meltingRange_max decimal(6,2),hardness decimal(6,2),validation varchar(20),starredProperty varchar(20))";
        database.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){}
}
