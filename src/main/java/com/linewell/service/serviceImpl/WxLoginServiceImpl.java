package com.linewell.service.serviceImpl;

import com.linewell.VO.Wxconstants;
import com.linewell.service.WxLoginService;
import com.linewell.utils.AuthUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("WxLoginService")
public class WxLoginServiceImpl implements WxLoginService {

    @Override
    public JSONObject doWxLogin(String jsCode) throws IOException {
        String authUrl
                = Wxconstants.WEIXIN_REQ_URL + "appid=" + Wxconstants.APP_ID + "&secret=" + Wxconstants.SECRET + "&js_code=" + jsCode + "&grant_type=" + Wxconstants.GRANT_TYPE;
        //2. 向微信发出请求
        JSONObject jsonObject = AuthUtil.doGetJson(authUrl);
        return jsonObject;
    }

}
