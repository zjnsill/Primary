package com.psi.shen.primary;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

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
    private static final String TAG_STARREDITEMS = "starredItems";

    public user_server(){}
    public signedUser SignIn(String phoneNum, String passcode) {
        String path = "http://118.25.122.232/android_connect/signIn.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
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
                    signedUser user = new signedUser.Builder(Name, Phone).Email(Email).Bio(Bio).errorCode(signedUser.SUCCESS).build();
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
        String path = "http://118.25.122.232/android_connect/register.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", user.getPhone());
            formBody.add("Password", Passcode);
            if(!user.getName().isEmpty())
                formBody.add("Name", user.getName());
            if(!user.getEmail().isEmpty())
                formBody.add("Email", user.getEmail());
            if(!user.getBio().isEmpty())
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
                    signedUser returnUser = new signedUser.Builder(null, null).errorCode(signedUser.USER_EXISTED).build();
                    return returnUser;
                } else if(errorCode == 2) { // unknown error
                    signedUser returnUser = new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR).build();
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

    public @signedUser.ERROR_CODES int changePasscode(String Phone, String OldPassword, String Passcode) {
        String path="http://118.25.122.232/android_connect/changePassword.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", Phone);
            formBody.add("OldPassword", OldPassword);
            formBody.add("Password", Passcode);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0)
                    return signedUser.SUCCESS;
                else if(errorCode == 1)
                    return signedUser.NO_SUCH_USER;
                else if(errorCode == 2)
                    return signedUser.INCORRECT_PASSWORD;
                else if(errorCode == 3)
                    return signedUser.UNKNOWN_ERROR;
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedUser.UNKNOWN_ERROR;
    }

    public signedUser changeProfile(String oldPhone, signedUser newProfile) { //including phone number;
        String path = "http://118.25.122.232/android_connect/changeProfile.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            String name = newProfile.getName();
            String phone = newProfile.getPhone();
            String email = newProfile.getEmail();
            String bio = newProfile.getBio();
            if(!oldPhone.isEmpty())
                formBody.add("OldPhone", oldPhone);
            if(!name.isEmpty())
                formBody.add("Name", name);
            if(!phone.isEmpty())
                formBody.add("Phone", phone);
            if(!email.isEmpty())
                formBody.add("Email", email);
            if(!bio.isEmpty())
                formBody.add("Bio", bio);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0) { // success
                    signedUser returnUser = new signedUser(newProfile, signedUser.SUCCESS);
                    return returnUser;
                } else if(errorCode == 1) { // user not exist
                    signedUser returnUser = new signedUser.Builder(null, null).errorCode(signedUser.NO_SUCH_USER).build();
                    return returnUser;
                } else if(errorCode == 2) { // changed phone already exist
                    signedUser returnUser = new signedUser.Builder(null, null).errorCode(signedUser.USER_EXISTED).build();
                    return returnUser;
                } else if(errorCode == 3) { // unknown error
                    signedUser returnUser = new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR).build();
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

    public signedUser addStarredItem(String Phone, String newStarredItem) { //return editted profile;
        String path = "http://118.25.122.232/android_connect/addStarredItem.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", Phone);
            formBody.add("NewStarredItem", newStarredItem);
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0) {
                    String name = jsonObject.getJSONObject(TAG_USER).getString(TAG_NAME);
                    String phone = jsonObject.getJSONObject(TAG_USER).getString(TAG_PHONE);
                    String starredItemstr = jsonObject.getJSONObject(TAG_USER).getString(TAG_STARREDITEMS);
                    return new signedUser.Builder(name, phone).errorCode(signedUser.SUCCESS).starredItems(starredItemstr).build();
                } else if(errorCode == 1) {
                    return new signedUser.Builder(null, null).errorCode(signedUser.NO_SUCH_USER).build();
                } else if (errorCode == 2) {
                    return new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR).build();
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public signedUser getStarredItem(String Phone) { //return whole profile;
        String path = "http://118.25.122.232/android_connect/addStarredItem.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", Phone);
            formBody.add("NewStarredItems", "");
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0) {
                    String name = jsonObject.getJSONObject(TAG_USER).getString(TAG_NAME);
                    String phone = jsonObject.getJSONObject(TAG_USER).getString(TAG_PHONE);
                    String starredItemstr = jsonObject.getJSONObject(TAG_USER).getString(TAG_STARREDITEMS);
                    return new signedUser.Builder(name, phone).errorCode(signedUser.SUCCESS).starredItems(starredItemstr).build();
                } else if(errorCode == 1) {
                    return new signedUser.Builder(null, null).errorCode(signedUser.NO_SUCH_USER).build();
                } else if (errorCode == 2) {
                    return new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR).build();
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public signedUser removeStarredItem(String Phone, ArrayList<String> StarredItem) { //return editted profile;
        String path = "http://118.25.122.232/android_connect/removeStarredItem.php";
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            formBody.add("Phone", Phone);
            formBody.add("RemoveStarredItems", StarredItem.toString());
            Request request = new Request.Builder().url(path).post(formBody.build()).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.i(TAG, "response.code() = " + response.code());
                Log.i(TAG, "response.message() = " + response.message());
                JSONObject jsonObject = new JSONObject(response.body().string());
                int errorCode = jsonObject.getInt(TAG_ERRORCODE);
                if(errorCode == 0) {
                    String name = jsonObject.getJSONObject(TAG_USER).getString(TAG_NAME);
                    String phone = jsonObject.getJSONObject(TAG_USER).getString(TAG_PHONE);
                    String starredItemstr = jsonObject.getJSONObject(TAG_USER).getString(TAG_STARREDITEMS);
                    return new signedUser.Builder(name, phone).errorCode(signedUser.SUCCESS).starredItems(starredItemstr).build();
                } else if(errorCode == 1) {
                    return new signedUser.Builder(null, null).errorCode(signedUser.NO_SUCH_USER).build();
                } else if (errorCode == 2) {
                    return new signedUser.Builder(null, null).errorCode(signedUser.UNKNOWN_ERROR).build();
                }
            } else {
                Log.e(TAG, "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
