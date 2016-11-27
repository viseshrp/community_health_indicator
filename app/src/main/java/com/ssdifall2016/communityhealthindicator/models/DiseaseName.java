package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by viseshprasad on 11/26/16.
 */

public class DiseaseName extends BaseModel {

    @SerializedName("diseaseName")
    private String diseaseName;

    @SerializedName("diseaseId")
    private String diseaseId;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }
}
