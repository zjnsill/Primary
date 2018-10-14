package com.psi.shen.primary;

import android.content.Context;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SMS_Service {
    private Context context;
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int SMScodeOk = 2;
    public static final int SMSWrong = 3;

    @IntDef({SUCCESS,ERROR,SMScodeOk,SMSWrong})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SMSERROR{}

    public SMS_Service(Context context){
        this.context=context;
    }
    public @SMSERROR int sendSMS(String PhoneNum,Context context){
        return SUCCESS;
    }
    public @SMSERROR int VerifySMScode(String PhoneNum,String code,Context context){
        return SMScodeOk;
    }
}
