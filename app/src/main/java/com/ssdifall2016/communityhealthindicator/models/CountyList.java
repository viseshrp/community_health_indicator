package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/24/16.
 */

public class CountyList extends BaseModel {

    @SerializedName("result")
    ArrayList<County> countyList;

    public ArrayList<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(ArrayList<County> countyList) {
        this.countyList = countyList;
    }

}
