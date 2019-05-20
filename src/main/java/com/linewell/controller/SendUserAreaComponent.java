package com.linewell.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linewell.dao.PoliceAffDao;
import com.linewell.dao.UserAreaDao;
import com.linewell.domain.UserArea;
import com.linewell.utils.HibernateUtil;
import com.linewell.webSocketThread.PoliceAffTherad;
import com.linewell.webSocketThread.UserAreaThread;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.Resource;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket/{userid}",configurator = SpringConfigurator.class)
public class SendUserAreaComponent {

    private Session session;

    @Resource
    private PoliceAffDao policeAffDao;

    private String pushId;

    private String loginUserId;

    @Resource
    private UserAreaDao userAreaDao;

    //记录每个用户下多个终端的连接
    private static ConcurrentHashMap<String, Session> webSocketSet
            = new ConcurrentHashMap<String, Session>();

    @OnOpen
    public void onOpen(@PathParam(value = "userid") String loginUserId, Session session){
        UserAreaThread thread = null;
        this.session = session;
        webSocketSet.put(loginUserId,session);
        this.loginUserId = loginUserId;
        thread=new UserAreaThread(userAreaDao,session);
        thread.start();
    }

    @OnMessage
    public void onMessage(String message){
        JSONObject js = JSON.parseObject(message);
        System.out.println("这是推送过来的message:"+js);
        System.out.println("这是推送过来的message里的puchid"+(String) js.get("pushId"));
        this.pushId = (String) js.get("pushId");
        if(this.pushId != null){
            PoliceAffTherad therad1 = new PoliceAffTherad(policeAffDao,this.pushId,webSocketSet);
            therad1.start();
        }
        if(js.get("userId") != null){
            UserArea userArea = userAreaDao.findByUserId((String) js.get("userId"));
            //如果用户不存在用户地址，则新建
            if(userArea == null){
                userArea = new UserArea();
                userArea.setUserId((String) js.get("userId"));
                if(js.get("address") != null){
                    userArea.setAddress((String) js.get("address"));
                }
                if(js.get("areaCode") != null){
                    userArea.setAreaCode((String) js.get("areaCode"));
                }
                userArea.setCode(0);
                HibernateUtil.save(userArea);
            }else{
                if(js.get("address") != null){
                    userArea.setAddress((String) js.get("address"));
                }
                if(js.get("areaCode") != null){
                    userArea.setAreaCode((String) js.get("areaCode"));
                }
                userArea.setCode(0);
                HibernateUtil.update(userArea);
            }

        }
    }


    public void getPushId(String pushId){
        this.pushId = pushId;
    }

    public void sendMessage(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }
}
