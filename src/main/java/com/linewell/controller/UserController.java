package com.linewell.controller;

import com.linewell.VO.ProjectVO;
import com.linewell.dao.UserDao;
import com.linewell.domain.User;
import com.linewell.service.UserService;
import com.linewell.utils.HibernateUtil;
import com.linewell.utils.PubUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> regist(HttpServletRequest req,@RequestParam(name = "userName",required=false,defaultValue = "")String userName,
                         @RequestParam(name = "telephone",required=false,defaultValue = "")String telephone,
                         @RequestParam(name = "password",required=false,defaultValue = "")String password){
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        String msg;
        //增加电话号码校验
        msg = PubUtils.validAddUser(telephone,userDao);
        if(StringUtils.isEmpty(msg)){
            user.setName(userName);
            user.setTelephone(telephone);
            user.setCreateTime(new Date());
            user.setPassword(PubUtils.md5(password));
            msg = userService.registUser(user);
            map.put("errCode", ProjectVO.SUCCESS_MSG);
        }else{
            map.put("errCode", ProjectVO.TELEPHONE_EXIST_MSG);
        }

        return map;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest req,@RequestParam(name = "userName",required=false,defaultValue = "")String userName,
                                     @RequestParam(name = "telephone",required=false,defaultValue = "")String telephone,
                                     @RequestParam(name = "userId",required=false,defaultValue = "")String userId) {
        Map<String, Object> map = new HashMap<>();
        String msg = "";
        //1.根据用户id查询用户
        User user = userDao.findById(userId);
        if(user != null){
            //2.校验手机是否重复
            if(StringUtils.isNotEmpty(telephone)){
                msg = validUpdateUser(user,telephone);
                if(StringUtils.isEmpty(msg)){
                    user.setTelephone(telephone);
                }
            }
            if (StringUtils.isNotEmpty(userName)){
                user.setName(userName);
            }
            user.setUpdateTime(new Date());
            HibernateUtil.update(user);
        }
        PubUtils.returnMsg(map,msg);
        return map;
    }

    private String validUpdateUser(User user, String telephone) {
        String msg = "";
        User tempUser = userDao.findByTelephone(telephone);
        if(tempUser != null && !telephone.equals(user.getTelephone())){
            msg = "telephone repeat error";
        }
        return msg;
    }
}
