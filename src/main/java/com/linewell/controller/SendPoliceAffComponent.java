package com.linewell.controller;


import com.linewell.dao.PoliceAffDao;
import com.linewell.webSocketThread.PoliceAffTherad;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.Resource;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/sendPoliceAffSocket",configurator = SpringConfigurator.class)
public class SendPoliceAffComponent {

   private Session session;

    @Resource
    private PoliceAffDao policeAffDao;

    private String pushId;

    //记录每个用户下多个终端的连接
    private static ConcurrentHashMap<String, Session> webSocketSet
            = new ConcurrentHashMap<String, Session>();

    @OnOpen
    public void onOpen(@PathParam("pushId")String pushId, Session session){
        PoliceAffTherad thread = null;
        thread=new PoliceAffTherad(policeAffDao,pushId,webSocketSet);
        thread.start();
    }

}
