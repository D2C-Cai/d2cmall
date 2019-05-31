package com.d2c.frame.backweb.control.bak;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
//@Controller
public class ErrorContorol {

    @RequestMapping(value = "/error")
    public String Error() {
        return "error/error";
    }

    @RequestMapping(value = "/err400")
    public String ERR400() {
        return "error/err400";
    }

    @RequestMapping(value = "/err403")
    public String ERR403() {
        return "error/err403";
    }

    @RequestMapping(value = "/err404")
    public String ERR404() {
        return "error/err404";
    }

    @RequestMapping(value = "/err500")
    public String ERR500() {
        return "error/err500";
    }

}
