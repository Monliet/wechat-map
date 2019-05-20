package com.linewell.utils;

import com.linewell.VO.ProjectVO;
import com.linewell.dao.UserDao;
import com.linewell.domain.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PubUtils {
    private static MD5 md5 = new MD5();

    public static String md5(String str) {
        return md5.getMD5ofStr(md5.getMD5ofStr(str));
    }

    public static Map<String,Object> returnMsg(Map<String,Object> map,String msg) {
        if(msg.contains("success")|| StringUtils.isEmpty(msg)){
            map.put("errCode","200");
        }else if(msg.contains("error") || msg.contains("fail")){
            map.put("errCode","500");
        }
        return map;
    }

    public static void setUser(HttpServletRequest request , User user){
        request.getSession(true).removeAttribute(ProjectVO.USER_SESSION_NAME) ;
        request.getSession(true).setAttribute(ProjectVO.USER_SESSION_NAME , user) ;
    }

    public static String validAddUser(String telephone, UserDao userDao){
        String msg = new String();
        User userTemp = userDao.findByTelephone(telephone);
        if(userTemp != null){
            msg = "user_regis";
        }
        return msg;
    }
}
