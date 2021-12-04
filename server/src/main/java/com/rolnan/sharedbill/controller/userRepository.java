package com.rolnan.sharedbill.controller;

import com.rolnan.sharedbill.domain.userInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface userRepository extends CrudRepository<userInfo, Integer> {
    List<userInfo> findByUserId(@Param("userId") Integer userId);
    List<userInfo> findByUserPhone(@Param("userPhone") String userPhone);
    List<userInfo> findByUserMail(@Param("userMail") String userMail);
    List<userInfo> findByUserPhoneOrUserMail(@Param("userPhone") String userPhone, @Param("userMail") String userMail);
}