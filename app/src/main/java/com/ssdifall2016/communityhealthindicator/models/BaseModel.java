package com.ssdifall2016.communityhealthindicator.models;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class BaseModel {
    private int statusCode;
    private String process;
    private String status;
    private String message;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}