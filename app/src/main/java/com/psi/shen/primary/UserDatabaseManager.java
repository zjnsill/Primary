package com.psi.shen.primary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        boolean[] temp = new boolean[4];
        temp[0] = starredListItem.isCould;
        temp[1] = starredListItem.isSelfCreated;
        temp[2] = starredListItem.isToBeClouded;
        temp[3] = starredListItem.isToBeDeleted;
        contentValues.put("property",tools.booleanArrayToString(temp));
        return contentValues;
    }

    public ArrayList<StarredListItem> GetAllItems(){
        ArrayList<StarredListItem> resultArray = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(currentUser,null,null,null,null,null,null);
        String name,namingStandard,component,forging,weldability,machining,surfTreat,corrResis;
        Double density,thermalExpan,thermalCon,specificHeat,resistivity,elasticmodu,poissonsRatio,meltingRangeMin,meltingRangeMax,dampingIndex,
                hardnessMin,hardnessMax,fractureTou;
        boolean[] property;
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex("name"));
            namingStandard = cursor.isNull(cursor.getColumnIndex("namingStandard"))? null:cursor.getString(cursor.getColumnIndex("namingStandard"));
            component = cursor.isNull(cursor.getColumnIndex("component"))? null:cursor.getString(cursor.getColumnIndex("component"));
            density = cursor.isNull(cursor.getColumnIndex("density"))? null:cursor.getDouble(cursor.getColumnIndex("density"));
            thermalExpan = cursor.isNull(cursor.getColumnIndex("thermalExpan"))? null:cursor.getDouble(cursor.getColumnIndex("thermalExpan"));
            thermalCon = cursor.isNull(cursor.getColumnIndex("thermalCon"))? null:cursor.getDouble(cursor.getColumnIndex("thermalCon"));
            specificHeat = cursor.isNull(cursor.getColumnIndex("specificHeat"))? null:cursor.getDouble(cursor.getColumnIndex("specificHeat"));
            resistivity = cursor.isNull(cursor.getColumnIndex("resistivity"))? null:cursor.getDouble(cursor.getColumnIndex("resistivity"));
            elasticmodu = cursor.isNull(cursor.getColumnIndex("elasticModu"))? null:cursor.getDouble(cursor.getColumnIndex("elasticModu"));
            poissonsRatio = cursor.isNull(cursor.getColumnIndex("poissonsRatio"))? null:cursor.getDouble(cursor.getColumnIndex("poissonsRatio"));
            meltingRangeMin = cursor.isNull(cursor.getColumnIndex("meltingRangeMin"))? null:cursor.getDouble(cursor.getColumnIndex("meltingRangeMin"));
            meltingRangeMax = cursor.isNull(cursor.getColumnIndex("meltingRangeMax"))? null:cursor.getDouble(cursor.getColumnIndex("meltingRangeMax"));
            dampingIndex = cursor.isNull(cursor.getColumnIndex("dampingIndex"))? null:cursor.getDouble(cursor.getColumnIndex("dampingIndex"));
            hardnessMin = cursor.isNull(cursor.getColumnIndex("hardnessMin"))? null:cursor.getDouble(cursor.getColumnIndex("hardnessMin"));
            hardnessMax = cursor.isNull(cursor.getColumnIndex("hardnessMax"))? null:cursor.getDouble(cursor.getColumnIndex("hardnessMax"));
            forging = cursor.isNull(cursor.getColumnIndex("forging"))? null:cursor.getString(cursor.getColumnIndex("forging"));
            weldability = cursor.isNull(cursor.getColumnIndex("weldability"))? null:cursor.getString(cursor.getColumnIndex("weldability"));
            machining = cursor.isNull(cursor.getColumnIndex("machining"))? null:cursor.getString(cursor.getColumnIndex("machining"));
            surfTreat = cursor.isNull(cursor.getColumnIndex("surfTreat"))? null:cursor.getString(cursor.getColumnIndex("surfTreat"));
            corrResis = cursor.isNull(cursor.getColumnIndex("corrResis"))? null:cursor.getString(cursor.getColumnIndex("corrResis"));
            fractureTou = cursor.isNull(cursor.getColumnIndex("fractureTou"))? null:cursor.getDouble(cursor.getColumnIndex("fractureTou"));
            property = tools.StringToBooleanArray(cursor.getString(cursor.getColumnIndex("property")));
            SingleAlloyItem singleAlloyItem = new SingleAlloyItem.Builder(name).NamingStandard(namingStandard).Density(density).Component(component).ThermalCon(thermalCon).ThermalExpan(thermalExpan)
                    .SpecificHeat(specificHeat).Resistivity(resistivity).ElasticModu(elasticmodu).PoissonRatio(poissonsRatio).MeltingRange(meltingRangeMin,meltingRangeMax).DampingIndex(dampingIndex)
                    .Hardness(hardnessMin,hardnessMax).Forging(forging).Weldability(weldability).Machining(machining).SurfaceTreatment(surfTreat).CorrisionResistance(corrResis).FractureTou(fractureTou).build();
            StarredListItem starredListItem = new StarredListItem(singleAlloyItem,property[0],property[1],property[2],property[3]);
            resultArray.add(starredListItem);
        }
        cursor.close();
        db.close();
        return resultArray;
    }
    public int getCount(){
        int i;
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(currentUser,null,null,null,null,null,null);
        db.close();
        i = cursor.getCount();
        cursor.close();
        return i;
    }

    public boolean hasItem(String name){
        SQLiteDatabase db = database.getReadableDatabase();
        String sql="select * from "+currentUser+" where name = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        boolean out = cursor.moveToNext();
        cursor.close();
        db.close();
        return out;
    }
    //this function use moveToNext may contain bug;

    public void insertNewTable(String newTableName){
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "create table "+newTableName+" (_id integer primary key autoincrement,name varchar(40),namingStandard varchar(40),component varchar(40)" +
                ", density decimal(6,2),thermalExpan decimal(6,2),thermalCon decimal(6,2),specificHeat decimal(6,2),resistivity decimal(6,2), elasticModu decimal(6,2)" +
                ",poissonsRatio decimal(6,2),meltingRangeMin decimal(6,2),meltingRangeMax decimal(6,2),dampingIndex decimal(6,2),hardnessMin decimal(6,2)" +
                ",hardnessMax decimal(6,2),forging text,weldability text,machining text,surfTreat text,corrResis text,fractureTou text,validation varchar(40),property varchar(10))";
        db.execSQL(sql);
        db.close();
    }

    public boolean hasTable(){
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "select count(*) from sqlite_master where type = 'table' and name = "+currentUser;
        boolean hasTable = false;
        try{
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToNext()){
            int count = cursor.getInt(0);
            if(count>0) hasTable = true;
        }
        }catch (Exception e){}
        return hasTable;
    }
    public void clearAllData(String tableName){
        SQLiteDatabase db = database.getWritableDatabase();
        String sql = "delete from"+tableName;
        db.execSQL(sql);
        db.close();
    }
    public void deleteTable(String userName){
        SQLiteDatabase db = database.getWritableDatabase();
        String sql = "drop table if exists"+userName;
        db.execSQL(sql);
        db.close();
        switchUser("tempUser");
    }
    public void switchUser(String anotherUser){
        this.currentUser = anotherUser;
    }
}
