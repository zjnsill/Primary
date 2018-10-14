package com.psi.shen.primary;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class user_server {

    private static final String TAG = "MainActivity";
    private static final String TAG_ERRORCODE = "errorCode";
    private static final String TAG_USER = "user";
    private static final String TAG_NAME = "Name";
    private static final String TAG_PHONE = "Phone";
    private static final String TAG_EMAIL = "Email";
    private static final String TAG_BIO = "Bio";

    public user_server(){}
    public signedUser SignIn(String name, String phoneNum, String passcode) {
        String path = "http://118.25.122.232/android_connect/signIn.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            if(name != null)
                formBody.add("Name", name);
            else if(phoneNum != null)
                formBody.add("Phone", phoneNum);
            formBody.add("Password", passcode);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0) { // success
                    JSONObject jsonUser = jsonObject.getJSONObject(TAG_USER);
                    String Name = jsonUser.getString(TAG_NAME);
                    String Phone = jsonUser.getString(TAG_PHONE);
                    String Email = jsonUser.getString(TAG_EMAIL);
                    String Bio = jsonUser.getString(TAG_BIO);
                    signedUser user = new signedUser(new signedUser.Builder(Name, Phone).Email(Email).Bio(Bio).errorCode(signedUser.SUCCESS));
                    return user;
                } else if(errorCode == 1) { // incorrect password
                    signedUser user = new signedUser(new signedUser.Builder(null, null).errorCode(signedUser.INCORRECT_PASSWORD));
                    return user;
                } else if(errorCode == 2) { // user not existed
                    signedUser user = new signedUser(new signedUser.Builder(null, null).errorCode(signedUser.NO_SUCH_USER));
                    return user;
                } else if(errorCode == 3) { // unknown error
                    signedUser user = new signedUser(new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR));
                    return user;
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public signedUser register(signedUser user,String Passcode) {
        String path = "http://118.25.122.232/android_connect/signIn.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Name", user.getName());
            formBody.add("Password", Passcode);
            formBody.add("Phone", user.getPhone());
            formBody.add("Email", user.getEmail());
            formBody.add("Bio", user.getBio());
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0) { // success
                    signedUser returnUser = new signedUser(user, signedUser.SUCCESS);
                    return returnUser;
                } else if(errorCode == 1) { // user already existed
                    signedUser returnUser = new signedUser(new signedUser.Builder(null, null).errorCode(signedUser.USER_EXISTED));
                    return returnUser;
                } else if(errorCode == 2) { // unknown error
                    signedUser returnUser = new signedUser(new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR));
                    return returnUser;
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePasscode(String Phone,String Passcode) {
        String path="http://118.25.122.232/android_connect/changePassword.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            if(!Phone.isEmpty())
                formBody.add("Phone", Phone);
            if(!Passcode.isEmpty())
                formBody.add("Password", Passcode);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0)
                    return true;
                else
                    return false;
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public signedUser changeProfile(signedUser newProfile) { //including phone number;
        return null;
    }

    public signedUser addStarredItem(String Phone, ArrayList<String> newStarredItem){
        return null;
    }//return editted profile;
}
