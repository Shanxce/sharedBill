package com.rolnan.sharedbill.controller;

import com.rolnan.sharedbill.domain.billDetail;
import com.rolnan.sharedbill.domain.billUser;
import com.rolnan.sharedbill.domain.userInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface billDetailRepository extends CrudRepository<billDetail, Integer> {
    List<billDetail> findByBillId(@Param("billId") Integer billId );

    @Query("SELECT new map(bd.billCount as count,bd.billPhotoAddr as photoAddr,bd.billClean as clean," +
            "bd.billDetailCreatetime as createtime,bd.billDescribe as describe,ai.assetsName as assetsName) " +
            "from billDetail bd, assetsInfo ai where bd.assetsId=ai.assetsId and bd.billDetailId=:billDetailid")
    List<HashMap<String,Object>>findRecordsInfo(@Param("billDetailid")Integer billDetailid);

    List<billDetail> findByUserIdAndBillCleanAndBillId(@Param("userId") Integer userId
            , @Param("billClean") Boolean billClean, @Param("billId") Integer billId);

    List<billDetail> findByBillDetailId(@Param("billDetailId") Integer billDetailId);
}