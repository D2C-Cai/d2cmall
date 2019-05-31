package com.d2c.flame.controller.webview;

import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.property.HttpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * WEB VIEW
 */
@Controller
@RequestMapping(value = "/v3/api/invoke")
public class WebViewController extends BaseController {

    @Autowired
    private HttpProperties httpProperties;

    /**
     * APP访问网页
     *
     * @param appId
     * @param token
     * @param url
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/{appId}", method = RequestMethod.GET)
    public void invoke(@PathVariable String appId, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String param = request.getQueryString();
        response.sendRedirect(httpProperties.getMobileUrl() + "/appToWap?" + param);
    }

}
