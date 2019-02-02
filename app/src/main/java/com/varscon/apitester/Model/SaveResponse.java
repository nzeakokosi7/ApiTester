package com.varscon.apitester.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveResponse {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("job")
    @Expose
    private String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "SaveResponse{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
