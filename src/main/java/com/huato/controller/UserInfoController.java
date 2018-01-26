package com.huato.controller;

import com.huato.entity.UserInfo;
import com.huato.service.DemoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoController {

    @Autowired
    DemoInfoService demoInfoService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public@ResponseBody
    String test(){
        UserInfo loaded = demoInfoService.findById(1);
        System.out.println("loaded="+loaded);
        return"ok";
    }

    @RequestMapping("/delete/{id}")
    public@ResponseBody String delete(@PathVariable("id") long id){
        demoInfoService.deleteFromCache(id);
        return"ok";
    }
    @RequestMapping("/test1")
    public@ResponseBody String test1(){
        demoInfoService.test();
        System.out.println("DemoInfoController.test1()");
        return"ok";
    }



}
