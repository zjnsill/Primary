package com.psi.shen.primary;

import android.os.Parcel;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(name != null)
            params.add(new BasicNameValuePair("Name", name));
        else if(phoneNum != null)
            params.add(new BasicNameValuePair("Phone", phoneNum));
        params.add(new BasicNameValuePair("Password", passcode));
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();

            JSONObject jsonObject = parseInfo(is);
            int errorCode = jsonObject.getInt(TAG_ERRORCODE);
            if(errorCode == 0) { // success
                JSONObject jsonUser = jsonObject.getJSONObject(TAG_USER);
                String Name = jsonUser.getString(TAG_NAME);
                String Phone = jsonUser.getString(TAG_PHONE);
                String Email = jsonUser.getString(TAG_EMAIL);
                String Bio = jsonUser.getString(TAG_BIO);
                signedUser user = new signedUser(new signedUser.Builder(Name, Phone, Email, Bio, signedUser.SUCCESS));
                return user;
            } else if(errorCode == 1) { // incorrect password
                signedUser user = new signedUser(new signedUser.Builder(null, null, signedUser.INCORRECT_PASSWORD));
                return user;
            } else if(errorCode == 2) { // user not existed
                signedUser user = new signedUser(new signedUser.Builder(null, null, signedUser.NO_SUCH_USER));
                return user;
            } else if(errorCode == 3) { // unknown error
                signedUser user = new signedUser(new signedUser.Builder(null, null, signedUser.UNKNOWN_ERROR));
                return user;
            }
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    public signedUser register(signedUser user,String Passcode) {
        String path = "http://118.25.122.232/android_connect/signIn.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Name", user.getName()));
        params.add(new BasicNameValuePair("Password", Passcode));
        params.add(new BasicNameValuePair("Phone", user.getPhone()));
        params.add(new BasicNameValuePair("Email", user.getEmail()));
        params.add(new BasicNameValuePair("Bio", user.getBio()));
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is=  httpEntity.getContent();

            JSONObject jsonObject = parseInfo(is);
            int errorCode = jsonObject.getInt(TAG_ERRORCODE);
            if(errorCode == 0) { // success
                signedUser returnUser = new signedUser(user, signedUser.SUCCESS);
                return returnUser;
            } else if(errorCode == 1) { // user already existed
                signedUser returnUser = new signedUser(new signedUser.Builder(null, null, signedUser.USER_EXISTED));
                return returnUser;
            } else if(errorCode == 2) { // unknown error
                signedUser returnUser = new signedUser(new signedUser.Builder(null, null, signedUser.UNKNOWN_ERROR));
                return returnUser;
            }
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    public boolean changePasscode(String Phone,String Passcode) {
        String path="http://118.25.122.232/android_connect/changePassword.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(!Phone.isEmpty())
            params.add(new BasicNameValuePair("Phone", Phone));
        if(!Passcode.isEmpty())
            params.add(new BasicNameValuePair("Password", Passcode));
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();

            JSONObject jsonObject = parseInfo(is);
            int errorCode = jsonObject.getInt(TAG_ERRORCODE);
            if(errorCode == 0)
                return true;
            else
                return false;
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
        return false;
    }

    public signedUser changeProfile(signedUser newProfile) { //including phone number;
        return null;
    }

    public signedUser addStarredItem(String Phone, ArrayList<String> newStarredItem){
        return null;
    }//return editted profile;

    private JSONObject parseInfo(InputStream in) throws IOException {
        BufferedReader  br=new BufferedReader(new InputStreamReader(in));
        StringBuilder sb=new StringBuilder();
        String line;
        String json;
        JSONObject jsonObject = null;
        while ((line=br.readLine())!=null){
            sb.append(line+"\n");
        }
        json = sb.toString();
        Log.i(TAG, "parseInfo: sb:"+json);
        try {
            jsonObject = new JSONObject(json);
        } catch(JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jsonObject;
    }
}
