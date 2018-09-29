package com.psi.shen.primary;

import android.os.Parcel;
import android.os.Parcelable;

public class signedUser implements Parcelable {
    private String Name;
    private String Email;
    private int Phone;
    private String Bio;

    public signedUser(Builder builder){
        this.Name = builder.Name;
        this.Bio = builder.Bio;
        this.Email = builder.Email;
        this.Phone = builder.Phone;
    }


    public static class Builder{
        private String Name,Email="",Bio="";
        private int Phone;

        public Builder(String name,int phone){
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
        public signedUser build(){
            return new signedUser(this);
        }
    }

    public String getName(){
        return Name;
    }

    public int getPhone() {
        return Phone;
    }

    public String getBio() {
        return Bio;
    }

    public String getEmail() {
        return Email;
    }
    //
    //Parcelable
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out,int flag){
        out.writeString(Name);
        out.writeInt(Phone);
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
        this.Phone = in.readInt();
        this.Email = in.readString();
        this.Bio = in.readString();
    }

    //default user
    static signedUser DefaultUser = new Builder("DefaultUser",00000000000).Bio("Hey, bad boy, you haven't " +
            "signed in").Email("AlloyProject@sjtu.edu.cn").build();

}

