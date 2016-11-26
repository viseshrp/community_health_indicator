package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class HealthOfficial extends User {

    @SerializedName("mapped_county")
    private String mappedCounty;

    @SerializedName("mapped_disease")
    private String mappedDisease;

    @SerializedName("mapped_county_id")
    private int mappedCountyId;

    @SerializedName("mapped_disease_id")
    private int mappedDiseaseId;

    public String getMappedCounty() {
        return mappedCounty;
    }

    public void setMappedCounty(String mappedCounty) {
        this.mappedCounty = mappedCounty;
    }

    public String getMappedDisease() {
        return mappedDisease;
    }

    public void setMappedDisease(String mappedDisease) {
        this.mappedDisease = mappedDisease;
    }

    public int getMappedCountyId() {
        return mappedCountyId;
    }

    public void setMappedCountyId(int mappedCountyId) {
        this.mappedCountyId = mappedCountyId;
    }

    public int getMappedDiseaseId() {
        return mappedDiseaseId;
    }

    public void setMappedDiseaseId(int mappedDiseaseId) {
        this.mappedDiseaseId = mappedDiseaseId;
    }
}
