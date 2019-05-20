package com.linewell.controller;

import com.linewell.VO.ProjectVO;
import com.linewell.dao.UserAreaDao;
import com.linewell.domain.UserArea;
import com.linewell.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userArea")
public class UserAreaController {

    @Autowired
    UserAreaDao userAreaDao;

    @RequestMapping("/getUserArea")
    @ResponseBody
    public Map<String,Object> regist(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //查询所有人位置
        List<UserArea> userAreas = userAreaDao.findByCode();
        map.put("errCode", ProjectVO.SUCCESS_MSG);
        map.put("data",userAreas);
        return map;
    }


    @Autowired
    SocketHandler handler;
    @RequestMapping("/sendUserArea")
    @ResponseBody
    public Map<String,Object> sendTo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //查询所有人位置
        List<UserArea> userAreas = userAreaDao.findByCode();
        boolean flag = handler.sendMessageToAllUsers(new TextMessage("111"));
        map.put("errCode", ProjectVO.SUCCESS_MSG);
        map.put("data",userAreas);
        return map;
    }

}
