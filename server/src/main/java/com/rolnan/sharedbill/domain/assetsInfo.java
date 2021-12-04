package com.rolnan.sharedbill.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="assetsInfo")
public class assetsInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="assetsId")
    private Integer assetsId;
    @Column(name="assetsUserId")
    private Integer assetsUserId;
    @Column(length=40,name="assetsName")
    private String assetsName;
    @Column(name="assetsNumber")
    private Double assetsNumber;
    @Column(name="assetsCreatetime")
    private LocalDateTime assetsCreatetime = LocalDateTime.now();
    @Column(name="assetsTrash")
    private Boolean assetsTrash=false;

    public Integer getId() {
        return assetsId;
    }
    public void setId(Integer assetsId) {
        this.assetsId = assetsId;
    }

    public Integer getUserid() {
        return assetsUserId;
    }
    public void setUserid(Integer assetUserId) {
        this.assetsUserId = assetUserId;
    }

    public String getName() {
        return assetsName;
    }
    public void setName(String assetsName) {
        this.assetsName = assetsName;
    }

    public Double getNumber() {
        return assetsNumber;
    }
    public void setNumber(Double assetsNumber) {
        this.assetsNumber = assetsNumber;
    }

    public LocalDateTime getCreatetime() {
        return assetsCreatetime;
    }

    public Boolean getTrash() {
        return assetsTrash;
    }
    public void setTrash(Boolean assetsTrash) {
        this.assetsTrash = assetsTrash;
    }
}