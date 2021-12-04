package com.rolnan.sharedbill.controller;

import com.rolnan.sharedbill.domain.billInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface billRepository extends CrudRepository<billInfo, Integer> {
    @Query("SELECT new map(bi.billId as id,bi.billName as name,bi.billIsshare as isshare) from billInfo bi, billUser bu where bi.billId=bu.billId and bu.userId=:userid")
    List<HashMap<String,Object> > findBill(@Param("userid")Integer userid);

    @Query("SELECT new map(ui.userId as uid,ui.userName as name) from userInfo ui, billUser bu where ui.userId=bu.userId and bu.billId=:billid")
    List<HashMap<String,Object> >findBillUser(@Param("billid")Integer billid);

    @Query("SELECT ui.userId as uid from userInfo ui, billUser bu where ui.userId=bu.userId and bu.billId=:billid")
    List<Integer >findBillUserId(@Param("billid")Integer billid);

}


