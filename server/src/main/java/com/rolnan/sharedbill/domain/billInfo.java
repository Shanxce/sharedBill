package com.rolnan.sharedbill.domain;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity // This tells Hibernate to make a table out of this class
@Table(name="billInfo")
@JsonFilter("billFilter")
public class billInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="billId")
    private Integer billId;
    @Column(length=40,name="billName")
    private String billName;
    @Column(name="billIsshare")
    private Boolean billIsshare;
    @Column(name="billCreatetime")
    private LocalDateTime billCreatetime = LocalDateTime.now();

    public Integer getId() {
        return billId;
    }
    public void setId(Integer billId) {
        this.billId = billId;
    }

    public String getName() {
        return billName;
    }
    public void setName(String billName) {
        this.billName = billName;
    }

    public Boolean getIsshare() {
        return billIsshare;
    }
    public void setIsshare(Boolean billIsshare) {
        this.billIsshare = billIsshare;
    }

    public LocalDateTime getCreatetime() {
        return billCreatetime;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "billInfo")
    private Set<billUser> billUser = new HashSet<billUser>();

}