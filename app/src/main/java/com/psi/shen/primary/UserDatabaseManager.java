package com.psi.shen.primary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDatabaseManager {
    private UserDatabase database;
    private assistingTools tools = new assistingTools();

    //private String initialTableName = "tempUser";

    private String currentUser;//(act as table name)if not loged in yet, let user be Temp(pay attention to capitalization)//or we can take another way,but i think it's simpler

    public UserDatabaseManager(Context context,String currentUser){
        this.currentUser = currentUser;
        database = new UserDatabase(context,this.currentUser,null,1);
    }

    private static UserDatabaseManager instance;
    public static synchronized UserDatabaseManager getInstance(Context context,String currentUser){
        if(instance==null){instance = new UserDatabaseManager(context,currentUser);}
        return instance;
    }

    public void addStarredItem(StarredListItem starredListItem){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = StarredItemToConVal(starredListItem);
        db.insert(currentUser,null,contentValues);
        db.close();
    }

    public void deleteStarredItem(String name){
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(currentUser,"name=?",new String[]{name});
        db.close();
    }

    public void updateStarredItem(StarredListItem starredListItem){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = StarredItemToConVal(starredListItem);
        db.update(currentUser,contentValues,"name=?",new String[]{starredListItem.getAlloyItem().getAlloyName()});
        db.close();
    }



    public ContentValues StarredItemToConVal(StarredListItem starredListItem){
        SingleAlloyItem singleAlloyItem =  starredListItem.getAlloyItem();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",singleAlloyItem.getAlloyName());
        contentValues.put("namingStandard",singleAlloyItem.getNamingStandard());
        contentValues.put("component",singleAlloyItem.getComponent());
        contentValues.put("density",singleAlloyItem.getDensity());
        contentValues.put("thermalExpan",singleAlloyItem.getThermalExpan());
        contentValues.put("thermalCon",singleAlloyItem.getThermalCon());
        contentValues.put("specificHeat",singleAlloyItem.getSpecificHeat());
        contentValues.put("resistivity",singleAlloyItem.getResistivity());
        contentValues.put("elasticModu",singleAlloyItem.getElasticModu());
        contentValues.put("poissonsRatio",singleAlloyItem.getPoissonsRatio());
        contentValues.put("meltingRangeMin",singleAlloyItem.getMeltingRange_Min());
        contentValues.put("meltingrangeMax",singleAlloyItem.getMeltingRange_Max());
        contentValues.put("dampingIndex",singleAlloyItem.getDampingIndex());
        contentValues.put("hardnessMin",singleAlloyItem.getHardness_Min());
        contentValues.put("hardnessMax",singleAlloyItem.getHardness_Max());
        contentValues.put("forging",singleAlloyItem.getForging());
        contentValues.put("weldability",singleAlloyItem.getWeldability());
        contentValues.put("machining",singleAlloyItem.getMachining());
        contentValues.put("surfTreat",singleAlloyItem.getSurfaceTreatment());
        contentValues.put("corrResis",singleAlloyItem.getCorrisionResistance());
        contentValues.put("fractureTou",singleAlloyItem.getFractureToughness());
        contentValues.put("validation",tools.booleanArrayToString(singleAlloyItem.Validation));
        boolean[] temp = new boolean[4];
        temp[0] = starredListItem.isCould;
        temp[1] = starredListItem.isSelfCreated;
        temp[2] = starredListItem.isToBeClouded;
        temp[3] = starredListItem.isToBeDeleted;
        contentValues.put("property",tools.booleanArrayToString(temp));
        return contentValues;
    }

    public ArrayList<StarredListItem> GetAllItems(){


    }









    public void insertNewTable(SQLiteDatabase database, String newTableName){
        String sql = "create table "+newTableName+" (_id integer primary key autoincrement,name varchar(40),namingStandard varchar(40),component varchar(40)" +
                ", density decimal(6,2),thermalExpan decimal(6,2),thermalCon decimal(6,2),specificHeat decimal(6,2),resistivity decimal(6,2), elasticModu decimal(6,2)" +
                ",poissonsRatio decimal(6,2),meltingRangeMin decimal(6,2),meltingRangeMax decimal(6,2),dampingIndex decimal(6,2),hardnessMin decimal(6,2)" +
                ",hardnessMax decimal(6,2),forging text,weldability text,machining text,surfTreat text,corrResis text,fractureTou text,validation varchar(40),property varchar(10))";
        database.execSQL(sql);
    }
}
