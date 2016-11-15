package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class HealthOfficial extends User {

    @SerializedName("mapped_county")
    private String mappedCounty;

    public String getMappedCounty() {
        return mappedCounty;
    }

    public void setMappedCounty(String mappedCounty) {
        this.mappedCounty = mappedCounty;
    }
}
