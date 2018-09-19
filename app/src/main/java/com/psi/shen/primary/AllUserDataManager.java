package com.psi.shen.primary;

import android.content.Context;

public class AllUserDataManager {
    private static String userName;
    private UserOwnDatabase userDatabase;
    public AllUserDataManager(String userName, Context context){
        this.userName = userName;
        userDatabase = new UserOwnDatabase(context,this.userName,null,1);
    }
    private static AllUserDataManager instance;
    public static synchronized AllUserDataManager getInstance(Context ctx){
        if(instance==null){instance = new AllUserDataManager(userName,ctx);}
        return instance;
    }
    

}
