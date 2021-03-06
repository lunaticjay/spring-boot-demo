package com.demo.web;

import com.demo.domain.entity.Config;
import com.demo.domain.entity.Language;
import com.demo.domain.entity.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miaorf on 2016/8/2.
 */
@RequestMapping("/hello")
@Controller
public class HelloController {

    @Autowired
    Config config;

    @Autowired
    Language language;

    @RequestMapping("/index")
    public String hello(Model model, @RequestParam(defaultValue = "Ryan") String name){
        model.addAttribute("name",name);
        return "index";
    }

    @ResponseBody
    @RequestMapping("/info")
    public Map info(@Valid @ModelAttribute("user")User user, Errors errors){

        Map map = new HashMap();
        if (errors.hasErrors()){
            map.put("error",errors.getAllErrors());
        }else{
            map.put("user",user);
        }

        map.put("config", config);
        map.put("language", language);
        return map;
    }

    @RequestMapping("/user")
    public String user(@Valid @ModelAttribute("user")User user, Errors errors, Model model, String xss,
                       @RequestParam(defaultValue = "true") boolean injection){
        if (injection) {
            xss = StringEscapeUtils.escapeHtml4(xss);
        }
        model.addAttribute("xss", xss);
        if (errors.hasErrors()){
            model.addAttribute("error",errors.getAllErrors());
        }else{
//            model.addAttribute("user",user);
        }

        return "user";
    }



    @RequestMapping(value = "/map.json", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map map(){
        Map map = new HashMap();
        map.put("name","Ryan");
        map.put("sex","man");
        map.put("age",18);
        List list = new ArrayList();
        list.add("red");
        list.add("black");
        list.add("blue");
        list.add("yellow");
        map.put("colors",list);
        return map;
    }
}
