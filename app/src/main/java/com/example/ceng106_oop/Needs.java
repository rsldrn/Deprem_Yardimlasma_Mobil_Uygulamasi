package com.example.ceng106_oop;

import com.google.gson.annotations.SerializedName;

public class Needs {
    @SerializedName("user_id")
    public String user_id;
    public String category;
    public String item;
    public String province;
    public String district;
    public String neighborhood;
    public String street;
    public String building_info;
    public String status = "beklemede";
}
