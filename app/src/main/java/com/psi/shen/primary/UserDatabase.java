package com.psi.shen.primary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    private String initTableName;
    public UserDatabase(Context context, String tableName, SQLiteDatabase.CursorFactory factory, int version){
        super(context,"LocalUserCache.db",factory,version);
        this.initTableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String sql = "create table "+initTableName+" (_id integer primary key autoincrement,name varchar(40),namingStandard varchar(40),component varchar(40)" +
                ", density decimal(6,2),thermalExpan decimal(6,2),thermalCon decimal(6,2),specificHeat decimal(6,2),resistivity decimal(6,2), elasticModu decimal(6,2)" +
                ",poissonsRatio decimal(6,2),meltingRangeMin decimal(6,2),meltingRangeMax decimal(6,2),dampingIndex decimal(6,2),hardnessMin decimal(6,2)" +
                ",hardnessMax decimal(6,2),forging text,weldability text,machining text,surfTreat text,corrResis text,fractureTou text,validation varchar(40),property varchar(10))";
        database.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){}

}