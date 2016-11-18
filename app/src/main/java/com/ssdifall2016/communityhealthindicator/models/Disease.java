package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class Disease extends BaseModel {
    @SerializedName("disease_list")
    ArrayList<String> diseaseList;

    public ArrayList<String> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(ArrayList<String> diseaseList) {
        this.diseaseList = diseaseList;
    }
}
