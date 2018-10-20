package com.psi.shen.primary;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
//passcode is not stored in local, which means when editing passcodes,infomations needs to be uoloaded immidiately
public class signedUser implements Parcelable {
    public static final int SUCCESS = 0;
    public static final int NO_SUCH_USER = 1;
    public static final int INCORRECT_PASSWORD = 2;
    public static final int USER_EXISTED = 3;
    public static final int UNKNOWN_ERROR = 4;

    @IntDef({SUCCESS, NO_SUCH_USER, INCORRECT_PASSWORD, USER_EXISTED, UNKNOWN_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ERROR_CODES {}

    private String Name;
    private String Email;
    private String Phone;
    private String Bio;
    private ArrayList<String> starredItemArray = new ArrayList<>();
    private @ERROR_CODES int errorCode;


    public signedUser(Builder builder){
        this.Name = builder.Name;
        this.Bio = builder.Bio;
        this.Email = builder.Email;
        this.Phone = builder.Phone;
        this.errorCode = builder.errorCode;
    }

    public signedUser(signedUser user, @ERROR_CODES int errorCode) {
        this.Name = user.Name;
        this.Phone = user.Phone;
        this.Email = user.Email;
        this.Bio = user.Bio;
        this.errorCode = errorCode;
    }


    public static class Builder{
        private String Name,Email="",Bio="";
        private String Phone;
        private @ERROR_CODES int errorCode=SUCCESS;

        public Builder(String name, String phone) {
            this.Name = name;
            this.Phone = phone;
        }
        public Builder Email(String email){
            this.Email = email;
            return this;
        }
        public Builder Bio(String bio){
            this.Bio = bio;
            return this;
        }

        public Builder errorCode(@ERROR_CODES int errorcode) {
            this.errorCode = errorcode;
            return this;
        }

        public signedUser build(){
            return new signedUser(this);
        }
    }

    public void addStarredItem(String itemName){
        starredItemArray.add(itemName);
    }
    public void initialStarredItem(ArrayList<String> starredList){
        this.starredItemArray = starredList;
    }

    public String getName(){
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getBio() {
        return Bio;
    }

    public String getEmail() {
        return Email;
    }

    public int getStarredItemCount(){
        return starredItemArray.size();
    }

    public @ERROR_CODES int getErrorCode() { return this.errorCode; }

    //
    //Parcelable
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out,int flag){
        out.writeString(Name);
        out.writeString(Phone);
        out.writeString(Email);
        out.writeString(Bio);
    }
    public static final Parcelable.Creator<signedUser> CREATOR = new Creator<signedUser>() {
        @Override
        public signedUser createFromParcel(Parcel source) {
            return new signedUser(source);
        }

        @Override
        public signedUser[] newArray(int size) {
            return new signedUser[size];
        }
    };
    public signedUser(Parcel in){
        this.Name = in.readString();
        this.Phone = in.readString();
        this.Email = in.readString();
        this.Bio = in.readString();
    }

    //default user
    static signedUser DefaultUser = new Builder("DefaultUser","00000000000").Bio("Hey, bad boy, you haven't " +
            "signed in").Email("AlloyProject@sjtu.edu.cn").errorCode(SUCCESS).build();

}

