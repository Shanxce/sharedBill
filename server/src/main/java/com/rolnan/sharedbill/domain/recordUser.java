package com.rolnan.sharedbill.domain;


import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="recordUser")
public class recordUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="recordUserId")
    private Integer recordUserId;
    @Column(name="billDetailId")
    private Integer billDetailId;
    @Column(name="userId")
    private Integer userId;

    public Integer getBilldetailId() {
        return billDetailId;
    }
    public void setBilldetailId(Integer billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Integer getUserid() {
        return userId;
    }
    public void setUserid(Integer userId) {
        this.userId = userId;
    }
}
