package com.alex.app.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.EditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {
    @NotEmpty(message="name cant be empty")
    private String name;
    @NotEmpty(message="last name cant be empty")
    private String last_name;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date_of_birth;
    @NotEmpty(message="number cant be empty")
    private String number;
    @NotEmpty(message="email cant be empty")
    private String email;
    @NotEmpty()
    private String city;
    @NotEmpty
    @Size(min = 0, max = 150, message = "message cant be greater then 150 symbols")
    private String expectedSalary;

    private List<WorkPlace> workPlaces = new ArrayList<>();
    private List<Education> educations = new ArrayList<>();

    @NotEmpty
    private String option = " ";
    @NotEmpty
    private String languages;

    private MultipartFile imageData;

    public Person(){};
    public Person(String name, String last_name, Date date_of_birth, String number,
                  String email, String expectedSalary, String city,
                  List<WorkPlace> workPlaces, List<Education> educations,
                  String option, MultipartFile imageData, String languages) {
        this.name = name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.number = number;
        this.email = email;
        this.expectedSalary = expectedSalary;
        this.workPlaces = workPlaces;
        this.educations = educations;
        this.option = option;
        this.imageData = imageData;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(@NonNull String last_name) {
        this.last_name = last_name;
    }
    public Date getDate_of_birth() {
        return date_of_birth;
    }
    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<WorkPlace> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(List<WorkPlace> workPlaces) {
        this.workPlaces = workPlaces;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public MultipartFile getImageData() {
        return imageData;
    }

    public void setImageData(MultipartFile imageData) {
        this.imageData = imageData;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", expected salary='" + expectedSalary + '\'' +
                '}';
    }
}
