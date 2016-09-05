package com.leoman.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yesong on 2016/9/1.
 */
@RequestMapping(value = "/admin/demo")
@Controller
public class DemoController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "system/demo/demo";
    }
}
