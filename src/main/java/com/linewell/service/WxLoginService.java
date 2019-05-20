package com.linewell.service;

import com.linewell.domain.User;
import net.sf.json.JSONObject;

import java.io.IOException;

public interface WxLoginService {

    public JSONObject doWxLogin(String jsCode) throws IOException;

}
