package com.psi.shen.primary;

public class UserLog {
    private String PassWord;
    private String PhoneNumber;
    //密码是否正确
    private boolean IfPasswordCorrect (String phoneNumber, String PassWord){
        boolean ifcorrect=true;
        return ifcorrect;
    }

    //新用户注册
    private boolean AddNewUser (String PhoneNumber, String Password){
        boolean ifsuccessful=true;
        return ifsuccessful;
    }

    //电话号码是否与已存在的重复
    private boolean IfPhoneExist (String PhoneNumber){
        boolean ifExist=false;
        return ifExist;
    }

    //忘记密码后修改密码
    private boolean ChangePassword (String phoneNumber, String NewPassword){
        boolean ifsuccessful=true;
        return ifsuccessful;
    }

}
