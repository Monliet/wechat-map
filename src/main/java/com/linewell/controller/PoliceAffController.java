package com.linewell.controller;


import com.linewell.VO.ProjectVO;
import com.linewell.dao.PoliceAffDao;
import com.linewell.dao.UserAreaDao;
import com.linewell.domain.PoliceAff;
import com.linewell.domain.UserArea;
import com.linewell.service.PoliceAffService;
import com.linewell.utils.HibernateUtil;
import com.linewell.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/PoliceAff")
public class PoliceAffController {

    @Autowired
    UserAreaDao userAreaDao;

    @Autowired
    PoliceAffDao policeAffDao;

    @Autowired
    PoliceAffService policeAffService;

    @RequestMapping(value = "/searchAllUser",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> searchAllUser(@RequestParam(name = "address",required=false,defaultValue = "")String address){
        Map<String, Object> map = new HashMap<>();
        String msg = new String();
        //1.根据地址查询
        List<UserArea> userArea = userAreaDao.findByAddressAndCode(address);
        //2.加入map
        PubUtils.returnMsg(map,msg);
        map.put("data",userArea);
        return map;
    }

    /**
     * 案情上传
     */
    @RequestMapping(value = "/pullPoliceAff",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> pullPoliceAff(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(name ="file",required=false) CommonsMultipartFile file,
                                            @RequestParam(name ="vid",required=false) CommonsMultipartFile vid) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String msg = new String();
        //1.创建时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileNameHead = sdf.format(new Date());
        //2.创建文件名
       String path=request.getSession().getServletContext().getRealPath("/")+"upload/";
       File tempFile=new File(path);
       if(!tempFile.exists()){
           tempFile.mkdirs();
       }
       path = path + fileNameHead+file.getOriginalFilename();
       File newFile=new File(path);
       //返回前端做展示
       String repPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/"+fileNameHead+file.getOriginalFilename();
       map.put("filePath",repPath);
       file.transferTo(newFile);
       return map;
    }

    /**
     * 查询案情
     */
    @RequestMapping(value = "/searchPoliceAff",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> searchPoliceAff(@RequestParam(name = "code",required=false)Integer code,
                                              @RequestParam(name = "pushName",required=false,defaultValue = "")String pushName,
                                              @RequestParam(name = "affMsg",required=false,defaultValue = "")String affMsg,
                                              @RequestParam(name = "address",required=false,defaultValue = "")String address,
                                              @RequestParam(name = "startTime",required=false,defaultValue = "")String startTime,
                                              @RequestParam(name = "endTime",required=false,defaultValue = "")String endTime){
        Map<String, Object> map = new HashMap<>();
        String msg = new String();
        //查询案情信息
        List<PoliceAff> policeAffList = policeAffDao.findAll(code,pushName,affMsg,address,startTime,endTime);
        //2.加入map
        PubUtils.returnMsg(map,msg);
        map.put("data",policeAffList);
        return map;
    }

    /**
     * 保存案情
     * @param userId 用户id;
     * @param pushId 推送用户id;
     * @param affMsg 事件详情;
     */
    @RequestMapping(value = "/savePoliceAff",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> savePoliceAff(@RequestParam(name = "userId",required=false,defaultValue = "")String userId,
                                            @RequestParam(name = "pushId",required=false,defaultValue = "")String pushId,
                                            @RequestParam(name = "affMsg",required=false,defaultValue = "")String affMsg,
                                            @RequestParam(name = "address",required=false,defaultValue = "")String address,
                                            @RequestParam(name = "filePath",required=false,defaultValue = "")String filePath){
        Map<String, Object> map = new HashMap<>();
        //1.查询当前用户是否有未读警情
        List<PoliceAff> unReadAffList = policeAffDao.findByReadOrUnread(1);
        if(unReadAffList.size() > 0){
            map.put("data", "[]");
            map.put("errCode", ProjectVO.HAD_UNREADAFF_MSG);
        }else{
            PoliceAff policeAff = new PoliceAff();
            policeAff.setUserId(userId);
            policeAff.setPushId(pushId);
            policeAff.setAffMsg(affMsg);
            policeAff.setAffAddress(address);
            policeAff.setCreateTime(new Date());
            policeAff.setFilePath(filePath);
            policeAff.setCode(1);
            HibernateUtil.save(policeAff);
            //policeAffService.sendUerid(pushId);
            map.put("errCode", ProjectVO.SUCCESS_MSG);
            map.put("data", policeAff);
        }
        return map;
    }

    /**
     * 读取案情
     * @param affId 警情id;
     */
    @RequestMapping(value = "/readPoliceAff",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> readPoliceAff(@RequestParam(name = "affId",required=false,defaultValue = "")String affId,
                                            @RequestParam(name = "fdMsg",required=false,defaultValue = "")String fdMsg,
                                            @RequestParam(name = "fdPath",required=false,defaultValue = "")String fdPath){
        Map<String, Object> map = new HashMap<>();
        //1.根据警情id查询警情
        PoliceAff aff = policeAffDao.findById(affId);
        if(aff != null){
            aff.setCode(0);
            aff.setFbMsg(fdMsg);
            aff.setFbfilePath(fdPath);
            HibernateUtil.update(aff);
            map.put("errCode", ProjectVO.SUCCESS_MSG);
        }else{
            map.put("errCode", ProjectVO.AFF_NONE_MSG);
        }
        return map;
    }
}
