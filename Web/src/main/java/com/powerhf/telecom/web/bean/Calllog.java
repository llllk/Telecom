package com.powerhf.telecom.web.bean;

public class Calllog {
    private Integer id;
    private String tel;
    private String calltime;
    private Integer sumcount;
    private Integer sumduration;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getSumcount() {
        return sumcount;
    }

    public void setSumcount(Integer sumcount) {
        this.sumcount = sumcount;
    }

    public Integer getSumduration() {
        return sumduration;
    }

    public void setSumduration(Integer sumduration) {
        this.sumduration = sumduration;
    }
}
