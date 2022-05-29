package com.example.myapplication.action;

import java.security.PrivateKey;

public class Profile {

    private String Name;
    private String CCCD;
    private String Heartbeat;
    private String Oxy;
    private String Phone;
    private String TempBody;

    public Profile() {
    }

    public Profile(String name, String CCCD, String heartbeat, String oxy, String phone, String tempBody) {
        Name = name;
        this.CCCD = CCCD;
        Heartbeat = heartbeat;
        Oxy = oxy;
        Phone = phone;
        TempBody = tempBody;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getHeartbeat() {
        return Heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        Heartbeat = heartbeat;
    }

    public String getOxy() {
        return Oxy;
    }

    public void setOxy(String oxy) {
        Oxy = oxy;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTempBody() {
        return TempBody;
    }

    public void setTempBody(String tempBody) {
        TempBody = tempBody;
    }

    @Override
    public String toString() {
        return
                "Name: " + Name + '\n' +
                "CCCD: " + CCCD + '\n' +
                "Heartbeat: " + Heartbeat + "(nhịp/phút)"+'\n' +
                "Oxy: " + Oxy + "(%)" +'\n' +
                "Phone: " + Phone + '\n' +
                "TempBody: " + TempBody + "(°C)"+'\n';
    }
}
