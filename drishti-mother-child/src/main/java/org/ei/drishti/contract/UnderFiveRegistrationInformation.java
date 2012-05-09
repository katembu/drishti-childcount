package org.ei.drishti.contract;

import java.text.MessageFormat;

public class UnderFiveRegistrationInformation {
    private String caseId;
    private String healthID;
    private String sex;
    private String firstname;
    private String lastname;
    private int birthweight;
    private String motherhealthid;
    private String householdhealthid;
    private String locationcode;
    private String clinicdelivery;

                    
    public UnderFiveRegistrationInformation(String caseId, String healthID, String sex, String firstname, String lastname, int birthweight, String motherhealthid, String householdhealthid, String locationcode, String clinicdelivery) {
        this.caseId = caseId;
        this.healthID = healthID;
        this.sex = sex;
        this.lastname = lastname;
        this.firstname = firstname;
        this.motherhealthid = motherhealthid;
        this.clinicdelivery = clinicdelivery;
        this.locationcode = locationcode;
        this.householdhealthid = householdhealthid;
        this.birthweight = 20;
    }

    public String caseId() {
        return caseId;
    }

    public String healthID() {
        return healthID;
    }

    public String sex() {
        return sex;
    }

    public String lastname() {
        return lastname;
    }

    public String firstname() {
        return firstname;
    }

    public String householdhealthid() {
        return householdhealthid;
    }

    public String locationcode() {
        return locationcode;
    }

    public String clinicdelivery() {
        return clinicdelivery;
    }

    public String motherhealthid() {
        return motherhealthid;
    }
        
    @Override
    public String toString() {
        return MessageFormat.format("Child Registration : healthID: {0}, Name: {1} {2}, Case: {3}. ", healthID, firstname, lastname, caseId);
    }
}
