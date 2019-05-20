package com.linewell.dao;

import com.linewell.domain.User;

import java.util.List;

public interface UserDao {
    public abstract List<User> findAll();

    public abstract User findByUserNameAndTelephone(String userName,String telephone);
    public abstract User findByTelephone(String telephone);
    public abstract User findById(String userId);
}
