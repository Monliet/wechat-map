package com.linewell.webSocketThread;

import com.linewell.dao.UserAreaDao;
import com.linewell.domain.UserArea;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.websocket.Session;
import java.util.*;

import static net.sf.json.JSONObject.fromObject;

public class UserAreaThread extends Thread {

    private UserAreaDao userAreaDao;

    private List<UserArea> currentMessage;

    private Session session;

    private int currentIndex;

    private JSONArray json;
    private JSONObject jo;

    public UserAreaThread(UserAreaDao userAreaDao,Session session) {
        currentMessage = new ArrayList<UserArea>();
        this.userAreaDao = userAreaDao;
        currentIndex = 0;//此时是0条消息
        this.session = session;
    }

    @Override
    public void run() {
        while(true){
            currentMessage = userAreaDao.findByCode();
            //TODO
            // 将创建对象写入构造，推送完成清空对象，防止一直new对象
            json= new JSONArray();
            jo = new JSONObject();
            //1.创建迭代器
            for(UserArea ua : currentMessage){
                jo.put("userId",ua.getUserId());
                jo.put("userArea",ua.getAreaCode());
                jo.put("currentIndex",new Random().nextInt(100));
                json.add(jo);
            }
            JSONObject jo2 = new JSONObject();
            jo2.put("aff",false);
            jo2.put("data",json);
            System.out.println(json.toString());
            session.getAsyncRemote().sendText(jo2.toString());
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
