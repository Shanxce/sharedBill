package com.rolnan.sharedbill.controller;

import com.rolnan.sharedbill.domain.billUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface billUserRepository extends CrudRepository<billUser, Integer> {
    List<billUser> findByBillId(@Param("billId") Integer billId);
    List<billUser> findByBillIdAndUserId(@Param("billId") Integer billId,@Param("userId") Integer userId);
}
