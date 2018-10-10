package com.psi.shen.primary;

import java.util.ArrayList;

public class user_server {
    public user_server(){}
    public signedUser SignIn(String phoneNum,String passcode){
        return null;
    }
    public signedUser register(signedUser user,String Passcode){return null;}
    public boolean changePasscode(String Phone,String Passcode){return false;}
    public signedUser changeProfile(signedUser newProfile){return null;}//including phone number;
    public signedUser addStarredItem(String Phone, ArrayList<String> newStarredItem){return null;}//return editted profile;
}
