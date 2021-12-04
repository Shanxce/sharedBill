package com.rolnan.sharedbill.domain;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


class IPMapKey implements Serializable {
    private static final long serialVersionUID = 3176972128965536016L;

    // 主键属性
    private Integer billId;
    private Integer userId;

    //无参数的public构造方法，必须要有
    public IPMapKey() {
    }

    //重写了一个带参数的构造方法
    public IPMapKey(Integer billId,Integer userId) {
        this.billId = billId;
        this.userId = userId;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    //覆盖hashCode方法，必须要有
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (billId.hashCode());
        result = PRIME * result + (userId.hashCode());
        return result;
    }

    //覆盖equals方法，必须要有
    @Override
    public boolean equals(Object obj) {
        IPMapKey objKey = (IPMapKey)obj;
        if(this.billId ==objKey.billId && this.userId ==objKey.billId)
            return true;
        return false;
    }
}


@Entity // This tells Hibernate to make a table out of this class
@IdClass(IPMapKey.class)
@Table(name="billUser")
public class billUser {
    @Id
    @Column(name="billId")
    private Integer billId;
    @Id
    @Column(name="userId")
    private Integer userId;
    @Column(length=16,name="userNickname")
    private String userNickname;
    @Column(name="billUserCreatetime")
    private LocalDateTime billUserCreatetime = LocalDateTime.now();
    @Column(name="billUserLeavetime")
    private LocalDateTime billUserLeavetime;

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

    public String getNickname() {
        return userNickname;
    }
    public void setNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public LocalDateTime getCreatetime() {
        return billUserCreatetime;
    }

    public LocalDateTime getLeavetime() {
        return billUserLeavetime;
    }
    public void setLeaveime(LocalDateTime billUserLeavetime) {
        this.billUserLeavetime=billUserLeavetime;
    }

    @ManyToOne
    @JoinColumn(name = "billId_billId")//外键
    private billInfo billInfo;
//    @OneToOne(mappedBy = "billUser")
//    private billInfo billInfo;
}