package com.example.medicom;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class IssueObject implements Serializable {

    private String description;
    private String userId;
    private String issueId;
    private String userDp;
    private boolean isOpen;
    private Timestamp time;
    private ArrayList<HashMap<String, Object>> responses;

    public IssueObject() {
    }

    IssueObject(DocumentSnapshot issue) {
        description = (String) issue.get("description");
        userId = (String) issue.get("userId");
        issueId = (String) issue.get("issueId");
        userDp = (String) issue.get("userDp");
        isOpen = (boolean) issue.get("open");
        time = (Timestamp) issue.get("time");
        responses = (ArrayList<HashMap<String, Object>>) issue.get("responses");
    }

    public ArrayList<HashMap<String, Object>> getResponses() {
        return responses;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getUserDp() {
        return userDp;
    }

    public String getUserId() {
        return userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setUserDp(String userDp) {
        this.userDp = userDp;
    }

    public void setisOpen(boolean open) {
        isOpen = open;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setResponses(ArrayList<HashMap<String, Object>> responses) {
        this.responses = responses;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
