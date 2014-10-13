package com.tinet.ccic.v3.app.demo.controller;

import com.framework.common.inc.PlatformConstants;
import com.framework.common.util.CookieUser;
import com.framework.common.util.CookieUtil;
import com.framework.common.util.JacksonUtil;
import com.tinet.ccic.v3.app.demo.pojo.DemoUserPojo;
import com.tinet.ccic.v3.app.demo.service.DemoUserService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-8-6
 * Time: 17:21
 * Email:johnny_lx@yahoo.com
 */
@Controller    //声明Action组件
@RequestMapping("/p/selectUsers")           //请求映射
public class DemoUserController {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(DemoUserController.class);

    //用于注入，(srping提供的) 默认按类型装配
    @Autowired
    private DemoUserService demoUserService;

    /**
     * @param request
     * @param response
     * @param id
     * @param name
     * @param address
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "to_select_user")
    public ModelAndView selectUser(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "id", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String id,
                                   @RequestParam(value = "name", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String name,
                                   @RequestParam(value = "address", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String address) throws Exception {


        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(id)) map.put("id", Integer.valueOf(id.trim()));
        if (StringUtils.isNotEmpty(name)) map.put("name", name.trim());
        if (StringUtils.isNotEmpty(address)) map.put("address", address.trim());
        List<DemoUserPojo> list = demoUserService.getList(map);
        map.put("result", list);

        /**
         * 创建cookie
         */
        String host = request.getRemoteAddr();
        CookieUser cookieUser = new CookieUser();
        cookieUser.setEntrpriseId("3000001");
        cookieUser.setUserName("admin");
        cookieUser.setHost(host);
        CookieUtil.saveCookie(cookieUser,response);

        return new ModelAndView("demo/user", map);
    }

    /**
     * @param request
     * @param response
     * @param name
     * @param address
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "to_add")
    @ResponseBody
    public Boolean addUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "name", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String name,
                           @RequestParam(value = "address", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String address) throws Exception {
        DemoUserPojo demoUserPojo = new DemoUserPojo();
        if (StringUtils.isNotEmpty(name)) demoUserPojo.setName(name.trim());
        if (StringUtils.isNotEmpty(address)) demoUserPojo.setAddress(address.trim());
        Boolean b = demoUserService.addUser(demoUserPojo);
        logger.debug("b=========" + b);
        return b;
    }

    /**
     * @param request
     * @param response
     * @param name
     * @param address
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "to_del")
    @ResponseBody
    public String delUser(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "id", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String id,
                          @RequestParam(value = "name", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String name,
                          @RequestParam(value = "address", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String address) throws Exception {
        boolean b = false;
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(id)) map.put("id", Integer.valueOf(id.trim()));
        if (StringUtils.isNotEmpty(name)) map.put("name", name.trim());
        if (StringUtils.isNotEmpty(address)) map.put("address", address.trim());
        if (StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(address) || StringUtils.isNotEmpty(id)) {
            b = demoUserService.delUser(map);
            logger.debug("b=========" + b);
        }
        if (b == false) {
            return "error";
        } else {
            return "seccess";
        }
    }

    @RequestMapping(value = "returnString")
//    @ResponseBody
    public String returnString(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "a", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String a) {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(a);
        String x = request.getParameter("b");
        String y = a;
//        return x+y;一
//        return "forward:/index.jsp";
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "returnJson.json")
    @ResponseBody
    public ObjectNode returnJson(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "a", required = true, defaultValue = PlatformConstants.DEFAULT_EMPTY_STR) String a) {
        response.setContentType("text/html;charset=UTF-8");
        ObjectNode json = JacksonUtil.getInstance().createObjectNode();
        json.put("message", a);
        json.put("message1", "123");
        json.put("message2", "abc");
        json.put("message3", "^&*");
        return json;
    }
}
