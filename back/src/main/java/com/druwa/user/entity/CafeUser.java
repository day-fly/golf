package com.druwa.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "1679_cafe_certification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeUser {
    @Id
    @Column(name = "user_dong_ho")
    private String userDongHo;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "gate_id")
    private String gateId;

    @Column(name = "check_type")
    private String checkType;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "etc")
    private String etc;

    @Column(name = "user_dept")
    private String userDept;

    @Column(name = "user_position")
    private String userPosition;

    @Column(name = "insert_reg_uptime")
    private String insertRegUptime;

    @Column(name = "insert_reg_date")
    private String insertRegDate;

    @Column(name = "insert_reg_time")
    private String insertRegTime;

}