package com.ssdifall2016.communityhealthindicator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class County extends BaseModel {

    @SerializedName("percent")
    private String percent;

    @SerializedName("measure")
    private String measure;

    @SerializedName("countyName")
    private String countyName;

    @SerializedName("mappingDist")
    private String mappingDist;

    @SerializedName("indDescription")
    private String indDescription;

    @SerializedName("avgNumDen")
    private String avgNumDen;

    @SerializedName("location")
    private String location;

    @SerializedName("diseaseDescription")
    private String diseaseDescription;

    @SerializedName("eventCount")
    private String eventCount;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getMappingDist() {
        return mappingDist;
    }

    public void setMappingDist(String mappingDist) {
        this.mappingDist = mappingDist;
    }

    public String getIndDescription() {
        return indDescription;
    }

    public void setIndDescription(String indDescription) {
        this.indDescription = indDescription;
    }

    public String getAvgNumDen() {
        return avgNumDen;
    }

    public void setAvgNumDen(String avgNumDen) {
        this.avgNumDen = avgNumDen;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public String getEventCount() {
        return eventCount;
    }

    public void setEventCount(String eventCount) {
        this.eventCount = eventCount;
    }
}
