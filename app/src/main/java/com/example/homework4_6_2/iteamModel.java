package com.example.homework4_6_2;

import java.util.Random;

public class iteamModel {
    String fullname;
    String mssv;
    String dob;
    String email;
    String birthplace;
    int id_student;

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    int color;

    public String getFullname() {
        return fullname;

    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }
    public int getColor() {
        return color;
    }

    public iteamModel(int id_student, String fullname, String mssv, String dob, String email, String birthplace) {
        this.id_student = id_student;
        this.fullname = fullname;
        this.mssv = mssv;
        this.dob = dob;
        this.email = email;
        this.birthplace = birthplace;
        Random random = new Random();
        color = random.nextInt();
    }
    public iteamModel(String fullname, String mssv, String dob, String email, String birthplace) {
        this.fullname = fullname;
        this.mssv = mssv;
        this.dob = dob;
        this.email = email;
        this.birthplace = birthplace;
        Random random = new Random();
        color = random.nextInt();
    }
}
