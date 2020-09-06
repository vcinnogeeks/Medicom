package com.example.medicom;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class IssueResponseObject implements Serializable {

    private String responseDescription;
    private String responseId;
    private Timestamp responseTime;
    private String responseDp;

    IssueResponseObject(HashMap<String, Object> response) {
        responseDescription = (String) response.get("responseDescription");
        responseId = (String) response.get("responseId");
        responseTime = (Timestamp) response.get("responseTime");
        responseDp = (String) response.get("responseDp");
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public String getResponseDp() {
        return responseDp;
    }

    public String getResponseId() {
        return responseId;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }
}
