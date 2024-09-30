package com.alex.app.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class WorkPlace {
    @NotEmpty
    private String nameOfCompany;
    @NotEmpty
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startOfWork;
    @NotEmpty
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date endOfWork;
    @NotEmpty
    private String nameOfRole;

    public WorkPlace(){}

    public WorkPlace(String nameOfCompany, String nameOfRole, Date startOfWork, Date endOfWork){
        this.nameOfCompany=nameOfCompany;
        this.nameOfRole=nameOfRole;
        this.startOfWork=startOfWork;
        this.endOfWork=endOfWork;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public Date getStartOfWork() {
        return startOfWork;
    }

    public void setStartOfWork(Date startOfWork) {
        this.startOfWork = startOfWork;
    }

    public Date getEndOfWork() {
        return endOfWork;
    }

    public void setEndOfWork(Date endOfWork) {
        this.endOfWork = endOfWork;
    }

    public String getNameOfRole() {
        return nameOfRole;
    }

    public void setNameOfRole(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    @Override
    public String toString() {
        return "WorkPlace{" +
                "nameOfCompany='" + nameOfCompany + '\'' +
                ", startOfWork=" + startOfWork +
                ", endOfWork=" + endOfWork +
                ", nameOfRole='" + nameOfRole + '\'' +
                '}';
    }
}
