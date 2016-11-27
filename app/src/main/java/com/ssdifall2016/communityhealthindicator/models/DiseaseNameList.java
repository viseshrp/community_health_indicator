package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/26/16.
 */

public class DiseaseNameList extends BaseModel{

    @SerializedName("diseaseList")
    ArrayList<DiseaseName> diseaseNameList;

    public ArrayList<DiseaseName> getDiseaseNameList() {
        return diseaseNameList;
    }

    public void setDiseaseNameList(ArrayList<DiseaseName> diseaseNameList) {
        this.diseaseNameList = diseaseNameList;
    }

}
