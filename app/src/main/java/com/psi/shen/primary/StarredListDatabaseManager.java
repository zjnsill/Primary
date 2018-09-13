package com.psi.shen.primary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class StarredListDatabaseManager {
    private StarredItemDatabase starredItemDatabase;
    private String table_StarredListDatabase = "StarredListData";

    public StarredListDatabaseManager(Context context){
        starredItemDatabase = new StarredItemDatabase(context,table_StarredListDatabase,null,1);
    }
    private static StarredListDatabaseManager instance;

    public static synchronized StarredListDatabaseManager getInstance(Context ctx){
        if(instance==null) {instance = new StarredListDatabaseManager(ctx);}
        return instance;
    }
    public void addStarredListItem(StarredListItem starredListItem){
        SingleAlloyItem singleAlloyItem = starredListItem.getAlloyItem();
        SQLiteDatabase db = starredItemDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",singleAlloyItem.getAlloyName());
        values.put("component",singleAlloyItem.getComponent());
        values.put("density", singleAlloyItem.getDensity());
        values.put("thermalExpan", singleAlloyItem.getThermalExpan());
        values.put("thermalCon",singleAlloyItem.getThermalCon());
        values.put("specificHeat",singleAlloyItem.getSpecificHeat());
        values.put("resistivity",singleAlloyItem.getResistivity());
        values.put("elasticModu",singleAlloyItem.getElasticModu());
        values.put("meltingRange_min",singleAlloyItem.getMeltingRange_Min());
        values.put("meltingRange_max",singleAlloyItem.getMeltingRange_Max());
        values.put("hardness",singleAlloyItem.getHardness());
        values.put("validation",new assistingTools().booleanArrayToString(singleAlloyItem.Validation));
        boolean[] temp  = new boolean[4];
        temp[0]=starredListItem.isCould;
        temp[1]=starredListItem.isSelfCreated;
        temp[2]=starredListItem.isToBeClouded;
        temp[3]=starredListItem.isToBeDeleted;
        values.put("starredProperty", new assistingTools().booleanArrayToString(temp));
        db.insert(table_StarredListDatabase,null,values);
        db.close();
    }

    public void deleteStarredListItem(String name){
        SQLiteDatabase db = starredItemDatabase.getWritableDatabase();
        db.delete(table_StarredListDatabase,"name=?",new String[]{name});
        db.close();
    }

    public void updateStarredListItem(StarredListItem starredListItem){
        SingleAlloyItem singleAlloyItem = starredListItem.getAlloyItem();
        boolean[] validation = singleAlloyItem.Validation;
        SQLiteDatabase db = starredItemDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",singleAlloyItem.getAlloyName());
        values.put("component",singleAlloyItem.getComponent());
        values.put("density", singleAlloyItem.getDensity());
        values.put("thermalExpan", singleAlloyItem.getThermalExpan());
        values.put("thermalCon",singleAlloyItem.getThermalCon());
        values.put("specificHeat",singleAlloyItem.getSpecificHeat());
        values.put("resistivity",singleAlloyItem.getResistivity());
        values.put("elasticModu",singleAlloyItem.getElasticModu());
        values.put("meltingRange_min",singleAlloyItem.getMeltingRange_Min());
        values.put("meltingRange_max",singleAlloyItem.getMeltingRange_Max());
        values.put("hardness",singleAlloyItem.getHardness());
        values.put("validation",new assistingTools().booleanArrayToString(singleAlloyItem.Validation));
        boolean[] temp  = new boolean[4];
        temp[0]=starredListItem.isCould;
        temp[1]=starredListItem.isSelfCreated;
        temp[2]=starredListItem.isToBeClouded;
        temp[3]=starredListItem.isToBeDeleted;
        values.put("starredProperty", new assistingTools().booleanArrayToString(temp));
        db.update(table_StarredListDatabase,values,"name=?",new String[]{singleAlloyItem.getAlloyName()});
        db.close();
    }

    public ArrayList<StarredListItem> getDataArray(){
        ArrayList<StarredListItem> outArray = new ArrayList<>();
        SQLiteDatabase db = starredItemDatabase.getReadableDatabase();
        Cursor cursor = db.query(table_StarredListDatabase,null,null,null,null,null,null);
        String name,component,validation,starredProperty;
        double density,thermalExpan,thermalCon,specificHeat,resistivity,elasticModu,meltingPoint_min,meltingPoint_max,
                hardness;
        boolean[] validation_boolean,starredProperty_boolean;
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
            component = cursor.getString(cursor.getColumnIndex("component"));
            density = cursor.getDouble(cursor.getColumnIndex("density"));
            thermalCon = cursor.getDouble(cursor.getColumnIndex("thermalCon"));
            specificHeat = cursor.getDouble(cursor.getColumnIndex("specificHeat"));
            thermalExpan = cursor.getDouble(cursor.getColumnIndex("thermalExpan"));
            resistivity = cursor.getDouble(cursor.getColumnIndex("resistivity"));
            elasticModu = cursor.getDouble(cursor.getColumnIndex("elasticModu"));
            meltingPoint_min = cursor.getDouble(cursor.getColumnIndex("meltingRange_min"));
            meltingPoint_max = cursor.getDouble(cursor.getColumnIndex("meltingRange_max"));
            hardness = cursor.getDouble(cursor.getColumnIndex("hardness"));
            validation = cursor.getString(cursor.getColumnIndex("validation"));
            starredProperty = cursor.getString(cursor.getColumnIndex("starredProperty"));
            validation_boolean = new assistingTools().StringToBooleanArray(validation);
            starredProperty_boolean = new assistingTools().StringToBooleanArray(starredProperty);
            SingleAlloyItem singleAlloyItem = new SingleAlloyItem(name,component,density,thermalExpan,
                    thermalCon,specificHeat,resistivity,elasticModu,meltingPoint_min,meltingPoint_max,hardness,validation_boolean);
            StarredListItem starredListItem = new StarredListItem(singleAlloyItem,starredProperty_boolean[0],starredProperty_boolean[1],
                    starredProperty_boolean[2],starredProperty_boolean[3]);
            outArray.add(starredListItem);
        }
        cursor.close();
        db.close();
        return outArray;
    }
    public int getCount(){
        SQLiteDatabase db = starredItemDatabase.getReadableDatabase();
        Cursor cursor = db.query(table_StarredListDatabase,null,null,null,null,null,null);
        db.close();
        return cursor.getCount();

    }
    public boolean hasItem(String name){
        SQLiteDatabase db = starredItemDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+table_StarredListDatabase+" where name = ?",new String[]{name});
        boolean out =cursor.moveToNext();
        cursor.close();
        db.close();
        return out;
    }




}
