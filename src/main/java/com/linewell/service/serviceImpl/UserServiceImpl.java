package com.linewell.service.serviceImpl;

import com.linewell.domain.User;
import com.linewell.service.UserService;
import com.linewell.utils.HibernateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String registUser(User user) {
        String msg = new String();
        user.setCreateTime(new Date());
        Boolean b = HibernateUtil.save(user);
        if(b){
            msg = "regis_success";
        }else {
            msg = "regis_fail";
        }
        return msg;
    }
}
