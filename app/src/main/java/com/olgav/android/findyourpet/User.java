package com.olgav.android.findyourpet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HOME on 30/04/2016.
 */
public class User implements Parcelable
{
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("phone")
    String phone;

    @SerializedName("address")
    String address;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public User(){}

    public User(String userId, String userName, String userPhone, String userAddress, String userEmail, String userPassword){
        this.setId(userId);
        this.setName(userName);
        this.setPhone(userPhone);
        this.setAddress(userAddress);
        this.setEmail(userEmail);
        this.setPassword(userPassword);
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @Override
    public String toString() {
        return name+phone+address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(password);
    }
}
