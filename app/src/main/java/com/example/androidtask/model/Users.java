package com.example.androidtask.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@androidx.room.Entity()
public class Users {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "first_name")//this is first_name
    String first_name;

    @ColumnInfo(name = "last_name")//this is last_name
    String last_name;

    @ColumnInfo(name = "email")//this is email
    String email;

    @ColumnInfo(name = "password")//this is password
    String password;

    @ColumnInfo(name = "mobile_number")//this is mobile_number
    String mobile_number;

    @ColumnInfo(name = "address")//this is address
    String address;

    @ColumnInfo(name = "gender")//this is gender
    String gender;

    public Users() {

    }

    public Users(String first_name, String last_name, String email, String password, String mobile_number, String address, String gender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.mobile_number = mobile_number;
        this.address = address;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
