package com.rolnan.sharedbill.domain;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity // This tells Hibernate to make a table out of this class
@Table(name="billDetail")
public class billDetail {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="billDetailId")
    private Integer billDetailId;
    @Column(name="billId")
    private Integer billId;
    @Column(name="userId")
    private Integer userId;
    @Column(name="assetsId")
    private Integer assetsId;
    @Column(name="billCount")
    private Double billCount;
    @Column(length=200,name="billDescribe")
    private String billDescribe;
    @Column(length=100,name="billPhotoAddr")
    private String billPhotoAddr;
    @Column(name="billClean")
    private Boolean billClean = false;
    @Column(name="billDetailCreatetime")
    private final LocalDateTime billDetailCreatetime = LocalDateTime.now();
    @Column(name="billDetailCleantime")
    private LocalDateTime billDetailCleantime = null;

    public Integer getBilldetailId() {
        return billDetailId;
    }
    public void setBilldetailId(Integer billDetailId) {
        this.billDetailId = billDetailId;
    }

    public Integer getBillId() {
        return billId;
    }
    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getUserid() {
        return userId;
    }
    public void setUserid(Integer userId) {
        this.userId = userId;
    }

    public Integer getAssetsid() {
        return assetsId;
    }
    public void setAssetsid(Integer assetsId) {
        this.assetsId = assetsId;
    }

    public Double getCount() {
        return billCount;
    }
    public void setCount(Double billCount) {
        this.billCount = billCount;
    }

    public String getBillDescribe() {
        return billDescribe;
    }
    public void setBillDescribe(String billDescribe) {
        this.billDescribe = billDescribe;
    }

    public String getPhotoaddr() {
        return billPhotoAddr;
    }
    public void setPhotoaddr(String billPhotoAddr) {
        this.billPhotoAddr = billPhotoAddr;
    }

    public LocalDateTime getCreatetime() {
        return billDetailCreatetime;
    }

    public Boolean getClean() {
        return billClean;
    }
    public void setClean(Boolean billClean) {
        this.billClean = billClean;
    }

    public LocalDateTime getCleantime() {
        return billDetailCleantime;
    }
    public void setCleanime(LocalDateTime billDetailCleantime) {
        this.billDetailCleantime =billDetailCleantime;
    }


}