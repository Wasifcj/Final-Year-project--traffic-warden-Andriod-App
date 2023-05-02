package com.cjz.wasif.trafficwarden;

public class DataSetFire {

    String DriverID;
    String name;

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    String phone;
    String profileImageUrl;

    public DataSetFire() {
    }

    public DataSetFire(String DriverID, String name, String phone, String profileImageUrl) {
        this.DriverID = DriverID;
        this.name = name;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
    }
}
