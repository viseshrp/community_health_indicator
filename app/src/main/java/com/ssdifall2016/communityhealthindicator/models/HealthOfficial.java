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
    private String mappedCountyId;

    @SerializedName("mapped_disease_id")
    private String mappedDiseaseId;

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

    public String getMappedCountyId() {
        return mappedCountyId;
    }

    public void setMappedCountyId(String mappedCountyId) {
        this.mappedCountyId = mappedCountyId;
    }

    public String getMappedDiseaseId() {
        return mappedDiseaseId;
    }

    public void setMappedDiseaseId(String mappedDiseaseId) {
        this.mappedDiseaseId = mappedDiseaseId;
    }
}
