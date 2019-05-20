package com.linewell.dao;

import com.linewell.domain.UserArea;

import java.util.List;

public interface UserAreaDao {

    public abstract List<UserArea> findByCode();

    public abstract UserArea findByUserId(String userId);

    public abstract List<UserArea> findByAddressAndCode(String address);

}
