package com.linewell.dao;

import com.linewell.domain.PoliceAff;

import java.util.Date;
import java.util.List;

public interface PoliceAffDao {

    List<PoliceAff> findAll(Integer code, String pushName, String affMsg, String address, String startTime,String endTime);

    //根据被推送人查询
    List<PoliceAff> findByPushId(String userId);

    //根据已读未读查询
    List<PoliceAff> findByReadOrUnread(Integer code);

    PoliceAff findById(String affId);
}
