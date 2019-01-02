package com.powerhf.telecom.web.bean;

public class Calllog {
    private int id;
    private String tel;
    private String calltime;
    private String sumcount;
    private String sumduration;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public String getSumcount() {
        return sumcount;
    }

    public void setSumcount(String sumcount) {
        this.sumcount = sumcount;
    }

    public String getSumduration() {
        return sumduration;
    }

    public void setSumduration(String sumduration) {
        this.sumduration = sumduration;
    }
}
