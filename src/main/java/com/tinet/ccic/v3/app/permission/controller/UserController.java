package com.tinet.ccic.v3.app.permission.controller;

import com.tinet.ccic.v3.app.permission.pojo.UserPojo;
import com.tinet.ccic.v3.app.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-10 19:55
 * Email:johnny_lx@yahoo.com
 */
@Controller
@RequestMapping("/p/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        if (null == name || null == pwd) {
            return new ModelAndView("app/usererror", null);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name);
            map.put("pwd", pwd);

            List<UserPojo> list = userService.userLogin(map);

            if (list.size() > 0) {
                map.put("message", list);
                return new ModelAndView("app/usersuccess", map);
            } else {
                return new ModelAndView("app/usererror", null);
            }
        }

    }
}
