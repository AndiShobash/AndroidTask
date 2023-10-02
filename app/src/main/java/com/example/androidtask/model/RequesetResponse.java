package com.example.androidtask.model;

import com.google.gson.annotations.SerializedName;

/************************************************ This is for the api request to get the data **********************************************************/
public class RequesetResponse {
    @SerializedName("count")
    private String count;
    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("probability")
    private String probability;

    public RequesetResponse(String count, String name, String gender, String probability) {
        this.count=count;
        this.name = name;
        this.gender = gender;
        this.probability = probability;
    }

    public RequesetResponse() {

    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }
}
