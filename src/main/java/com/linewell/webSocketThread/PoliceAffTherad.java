package com.linewell.webSocketThread;

import com.linewell.controller.SendUserAreaComponent;
import com.linewell.dao.PoliceAffDao;
import com.linewell.domain.PoliceAff;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class PoliceAffTherad extends Thread {

    private PoliceAffDao policeAffDao;

    List<PoliceAff> policeAffList;

    private String pushId;

    private JSONArray json;
    private JSONObject jo;

    private  ConcurrentHashMap<String, Session> webSocketSet;

    public PoliceAffTherad(PoliceAffDao policeAffDao,String pushId,
                           ConcurrentHashMap<String, Session> webSocketSet) {
        policeAffList = new ArrayList<PoliceAff>();
        this.policeAffDao = policeAffDao;
        this.pushId = pushId;
        this.webSocketSet = webSocketSet;
    }

    @Override
    public void run() {
            //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
            System.out.println("这是警情推送在线程的id:"+pushId);
            policeAffList
                    = policeAffDao.findByPushId(pushId);
            //TODO
            // 将创建对象写入构造，推送完成清空对象，防止一直new对象
            jo = new JSONObject();
            //1.创建迭代器
            jo.put("data",policeAffList);
            jo.put("aff",true);
            System.out.println("这是警情推送"+jo.toString());
            if(policeAffList.size() > 0){
                webSocketSet.get(pushId).getAsyncRemote().sendText(jo.toString());
            }
    }
}
