package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/26/16.
 */

public class CountyNameList extends BaseModel{

    @SerializedName("countyList")
    ArrayList<CountyName> countyNameList;

    public ArrayList<CountyName> getCountyNameList() {
        return countyNameList;
    }

    public void setCountyNameList(ArrayList<CountyName> countyNameList) {
        this.countyNameList = countyNameList;
    }
}
