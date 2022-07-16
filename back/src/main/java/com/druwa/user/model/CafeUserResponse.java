package com.druwa.user.model;

import com.druwa.user.entity.CafeUser;

public class CafeUserResponse {

    private String userDongHo;
    private String userName;
    private String gateId;
    private String checkType;
    private String userType;
    private String etc;
    private String userDept;
    private String userPosition;
    private String insertRegUptime;
    private String insertRegDate;
    private String insertRegTime;

    public CafeUserResponse(CafeUser cafeUser) {

        if(cafeUser == null) return;

        this.userDongHo = cafeUser.getUserDongHo();
        this.userName = cafeUser.getUserName();
        this.gateId = cafeUser.getGateId();
        this.checkType = cafeUser.getCheckType();
        this.userType = cafeUser.getUserType();
        this.etc = cafeUser.getEtc();
        this.userDept = cafeUser.getUserDept();
        this.userPosition = cafeUser.getUserPosition();
        this.insertRegUptime = cafeUser.getInsertRegUptime();
        this.insertRegDate = cafeUser.getInsertRegDate();
        this.insertRegTime = cafeUser.getInsertRegTime();
    }

    public String getUserDongHo() {
        return userDongHo;
    }

    public String getUserName() {
        return userName;
    }

    public String getGateId() {
        return gateId;
    }

    public String getCheckType() {
        return checkType;
    }

    public String getUserType() {
        return userType;
    }

    public String getEtc() {
        return etc;
    }

    public String getUserDept() {
        return userDept;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public String getInsertRegUptime() {
        return insertRegUptime;
    }

    public String getInsertRegDate() {
        return insertRegDate;
    }

    public String getInsertRegTime() {
        return insertRegTime;
    }
}
