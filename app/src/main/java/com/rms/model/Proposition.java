package com.rms.model;

/**
 * Created by rms on 01/05/2017.
 */

public class Proposition {

    public String userID;
    public String proposition;

    public Proposition() {
    }

    public Proposition(String userID, String proposition) {
        this.userID = userID;
        this.proposition = proposition;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProposition() {
        return proposition;
    }

    public void setProposition(String proposition) {
        this.proposition = proposition;
    }
}
