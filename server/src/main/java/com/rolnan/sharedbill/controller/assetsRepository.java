package com.rolnan.sharedbill.controller;

import com.rolnan.sharedbill.domain.assetsInfo;
import com.rolnan.sharedbill.domain.userInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface assetsRepository extends CrudRepository<assetsInfo, Integer> {
    List<assetsInfo> findByAssetsId(@Param("assetsId") Integer assetsId);
    List<assetsInfo> findByAssetsUserId(@Param("assetsUserId") Integer assetsUserId);
    List<assetsInfo> findByAssetsUserIdAndAssetsTrash(@Param("assetsUserId") Integer assetsUserId, @Param("assetsTrash") Boolean assetsTrash);


    @Query("SELECT new map(bd.billDetailId as recordId,bd.billId as billId,bd.billCount as count," +
            "bd.billDescribe as describe,ai.assetsId as assetsId,ai.assetsName as assetsName," +
            "ai.assetsCreatetime as createtime,bi.billIsshare as share) from assetsInfo ai" +
            ", billDetail bd,billInfo bi where ai.assetsId=:id and ai.assetsId=bd.assetsId " +
            "and bi.billId=bd.billId")
    List<HashMap<String,Object>>getRecords(@Param("id")Integer id);

    @Query("select new map(t.assetsName as name, t.assetsId as id, t.assetsNumber as amount) from assetsInfo t where t.assetsUserId=:id")
    List<HashMap<String, Object>>getAllAssets(@Param("id") Integer id);
}