package com.linewell.controller;

import com.linewell.VO.ProjectVO;
import com.linewell.dao.UserAreaDao;
import com.linewell.dao.UserDao;
import com.linewell.domain.User;
import com.linewell.domain.UserArea;
import com.linewell.service.WxLoginService;
import com.linewell.utils.HibernateUtil;
import com.linewell.utils.PubUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    WxLoginService wxLoginService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserAreaDao userAreaDao;

    @RequestMapping(value = "/getCode",method = {RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> findAllUsers(@RequestParam(name = "js_code",required=false,defaultValue = "")String jsCode) throws IOException {
        Map<String,Object> map = new HashMap<>();
        JSONObject jsonObject = wxLoginService.doWxLogin(jsCode);
        String errCode = String.valueOf(jsonObject.get("errcode"));
        if(!"null".equals(errCode) && StringUtils.isNotEmpty(errCode)){
            map.put("errCode",jsonObject.get("errcode"));
        }else{
            map.put("errCode",ProjectVO.SUCCESS_MSG);
        }
        map.put("data",jsonObject);
        System.out.println(map);
        return map;
    }

    @RequestMapping(value = "/login",method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> logIn(HttpServletRequest request, @RequestParam(name = "js_code",required=false,defaultValue = "")String jsCode,
                                     @RequestParam(name = "userName",required=false,defaultValue = "")String userName,
                                     @RequestParam(name = "telephone",required=false,defaultValue = "")String telephone,
                                     @RequestParam(name = "password",required=false,defaultValue = "")String password,
                                     @RequestParam(name = "areaCode",required=false,defaultValue = "")String areaCode,
                                     @RequestParam(name = "address",required=false,defaultValue = "")String address){
        Map<String,Object> map = new HashMap<>();
        JSONObject jsonObject = null;
        try {
            //1.校验网关
            jsonObject = wxLoginService.doWxLogin(jsCode);
            String errCode = String.valueOf(jsonObject.get("errcode"));
            if(!"null".equals(errCode) && StringUtils.isNotEmpty(errCode)){
                map.put("errCode",jsonObject.get("errcode"));
            }else{
                //2.校验用户和密码
                //2.1先校验用户是否存在
                User tempUser = userDao.findByUserNameAndTelephone(userName,telephone);
                if(tempUser != null){
                    //2.2校验用户密码
                    if(PubUtils.md5(password).equals(tempUser.getPassword())){
                        map.put("errCode",ProjectVO.SUCCESS_MSG);
                        jsonObject.put("userId",tempUser.getId());
                        //2.3设置session并保存登陆时间
                        tempUser.setLoginTime(new Date());
                        HibernateUtil.update(tempUser);
                        PubUtils.setUser(request,tempUser);
                    }else {
                        map.put("errCode",ProjectVO.PASSWORD_ERR_MSG);
                    }
                }else{
                    //返回用户不存在信息
                    map.put("errCode",ProjectVO.USER_NOTREGIST_MSG);
                }
            }
            map.put("data",jsonObject);
        } catch (IOException e) {
            map.put("errCode",ProjectVO.NET_ERROR_MSG);
            logger.error(e.toString());
        }
        return map;
    }

    @RequestMapping(value = "/logOut",method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> logOut(HttpServletRequest request,
                                     @RequestParam(name = "telephone",required=false,defaultValue = "")String telephone) {
        Map<String,Object> map = new HashMap<>();
        //1.将用户地理位置信息表字段置为1
        User user = userDao.findByTelephone(telephone);
        UserArea userArea = userAreaDao.findByUserId(user.getId());
        userArea.setCode(1);
        HibernateUtil.update(userArea);
        //2.清除session
        request.getSession().removeAttribute(ProjectVO.USER_SESSION_NAME);
        map.put("errCode",ProjectVO.SUCCESS_MSG);
        return map;
    }
}
