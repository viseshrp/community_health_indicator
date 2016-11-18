package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class County extends BaseModel {
    @SerializedName("county_list")
    ArrayList<String> countyList;

    public ArrayList<String> getCountyList() {
        return countyList;
    }

    public void setCountyList(ArrayList<String> countyList) {
        this.countyList = countyList;
    }
}
