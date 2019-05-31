package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.Theme;
import com.d2c.content.model.Theme.ThemeType;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeTagSearcher;
import com.d2c.content.service.ThemeService;
import com.d2c.content.service.ThemeTagService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

@Controller
@RequestMapping("/theme")
public class ThemeController extends BaseController {

    @Autowired
    private ThemeService themeService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ThemeTagService themeTagService;

    /**
     * 专题详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, ModelMap model) {
        Theme theme = themeService.findById(id);
        model.put("theme", theme);
        return "cms/theme_page";
    }

    /**
     * 网页详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public void html(HttpServletResponse response, String url) {
        String result = "";
        try {
            URL targetUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
            StringBuffer strBuffer = new StringBuffer();
            while ((result = in.readLine()) != null) {
                strBuffer.append(result);
            }
            result = strBuffer.toString();
            in.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        PrintWriter writer = null;
        try {
            response.setHeader("content-type", "text/html;charset=UTF-8");
            writer = response.getWriter();
            writer.write(result == null ? "" : result);
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 买手商家中心标签
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.GET)
    public String themeTags(ModelMap model, PageModel page) {
        PageResult<ThemeTag> pager = new PageResult<>();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            if (memberInfo.getPartnerId() != null) {
                Partner partner = partnerService.findById(memberInfo.getPartnerId());
                if (partner != null && partner.getStatus() == 1) {
                    ThemeTagSearcher searcher = new ThemeTagSearcher();
                    searcher.setType(ThemeType.WECHAT.name());
                    if (partner.getLevel() == 2) {
                        searcher.setFix(1);
                    }
                    pager = themeTagService.findBySearcher(searcher, page);
                }
            }
        } catch (NotLoginException e) {
        }
        model.put("themeTag", pager);
        return "cms/college";
    }

    /**
     * 根据标签id查找
     *
     * @return
     */
    @RequestMapping(value = "/tag/{id}", method = RequestMethod.GET)
    public String themeList(@PathVariable("id") Long tagId, PageModel page, ModelMap model) {
        PageResult<Theme> pager = themeService.findByTagId(tagId, page);
        model.put("themes", pager);
        return "cms/college_details";
    }

}
