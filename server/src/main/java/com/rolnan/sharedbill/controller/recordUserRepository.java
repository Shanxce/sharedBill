package com.rolnan.sharedbill.controller;

import com.rolnan.sharedbill.domain.recordUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
public interface recordUserRepository extends CrudRepository<recordUser, Integer> {

    List<recordUser> findByBillDetailId(@Param("billDetailId") Integer billDetailId);

    @Query("SELECT new map(ru.userId as uid,ui.userName as name) from recordUser as ru,userInfo as ui " +
            "where ru.billDetailId=:recordid and ru.userId=ui.userId")
    List<HashMap<String,Object>>useRecordId(@Param("recordid") Integer recordid);
}
