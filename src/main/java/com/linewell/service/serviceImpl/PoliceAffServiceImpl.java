package com.linewell.service.serviceImpl;

import com.linewell.controller.SendPoliceAffComponent;
import com.linewell.controller.SendUserAreaComponent;
import com.linewell.service.PoliceAffService;
import org.springframework.stereotype.Service;


@Service("PoliceAffService")
public class PoliceAffServiceImpl implements PoliceAffService {


    //声明websocket连接类
    private SendUserAreaComponent websocket = new SendUserAreaComponent();

    @Override
    public Boolean sendUerid(String userId) {
        Boolean b = false;
        if(userId != null){
            websocket.getPushId(userId);
            b = true;
        }
        return b;
    }
}
