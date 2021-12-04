package com.rolnan.sharedbill.domain;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="userInfo")
@JsonFilter("userFilter")
public class userInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="userId")
    private Integer userId;
    @Column(length=16,name="userName")
    private String userName;

    @Column(length=11,name="userPhone")
    private String userPhone;
    @Column(length=40,name="userMail")
    private String userMail;
    @Column(name="userPermission")
    private Integer userPermission;
    @Column(name="userCreatetime")
    private LocalDateTime userCreatetime = LocalDateTime.now();
    @Column(length=255,name="userPasswd")
    private String userPasswd;

    public Integer getId() {
        return userId;
    }
    public void setId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return userName;
    }
    public void setName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return userPhone;
    }
    public void setPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getMail() {
        return userMail;
    }
    public void setMail(String userMail) {
        this.userMail = userMail;
    }

    public Integer getPermission() {
        return userPermission;
    }
    public void setPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }

    public LocalDateTime getCreatetime() {
        return userCreatetime;
    }

    public String getPasswd() {
        return userPasswd;
    }
    public void setPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }
}