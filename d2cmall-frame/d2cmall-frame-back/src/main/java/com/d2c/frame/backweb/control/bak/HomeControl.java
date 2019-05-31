package com.d2c.frame.backweb.control.bak;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
//@Controller
public class HomeControl {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/sessionout")
    public String sessionOut() {
        return "sessionout";
    }

}
