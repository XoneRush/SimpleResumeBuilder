package com.alex.app.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Education {
    private String eduName;
    private String major;
    private String formOfEdu;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateOfEnd;

    public Education(){}

    public Education(String eduName, String major, String formOfEdu, Date dateOfEnd) {
        this.eduName = eduName;
        this.major = major;
        this.formOfEdu = formOfEdu;
        this.dateOfEnd = dateOfEnd;
    }

    public String getEduName() {
        return eduName;
    }

    public void setEduName(String eduName) {
        this.eduName = eduName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getFormOfEdu() {
        return formOfEdu;
    }

    public void setFormOfEdu(String formOfEdu) {
        this.formOfEdu = formOfEdu;
    }

    public Date getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(Date dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    @Override
    public String toString() {
        return "Education{" +
                "eduName='" + eduName + '\'' +
                ", major='" + major + '\'' +
                ", formOfEdu='" + formOfEdu + '\'' +
                ", dateOfEnd=" + dateOfEnd +
                '}';
    }
}
