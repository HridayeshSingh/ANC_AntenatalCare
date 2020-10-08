package com.example.anc_antenatalcare;

public class users {

    public String patName, age, husName, address, phn, opd, hosName;

    public users(){

    }

    public users(String patName, String age, String husName, String address, String phn, String opd, String hosName) {
        this.patName = patName;
        this.age = age;
        this.husName = husName;
        this.address = address;
        this.phn = phn;
        this.opd = opd;
        this.hosName = hosName;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHusName() {
        return husName;
    }

    public void setHusName(String husName) {
        this.husName = husName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

}
