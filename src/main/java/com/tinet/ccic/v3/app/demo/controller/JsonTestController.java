package com.tinet.ccic.v3.app.demo.controller;

import com.tinet.ccic.v3.app.demo.page.Shop;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-8 21:12
 * Email:johnny_lx@yahoo.com
 */

@Controller
@RequestMapping("/p/kfc")
public class JsonTestController {

    /**
     * 把这个java bean用json的格式输出,已经自动支持
     *
     * @return
     */
    @RequestMapping(value = "buy", method = RequestMethod.GET)
    @ResponseBody
    public Shop getShopInJson(HttpServletRequest request, HttpServletResponse response) {

        Shop shop = new Shop();
        shop.setName(request.getParameter("name"));
        shop.setStaffName(new String[]{"mkyong1", "mkyong2"});
        return shop;
    }

    /**
     * Jackson 原生写法 ，二次封装见JacksonUtil.java
     *
     * @return
     */
    @RequestMapping(value = "sell", method = RequestMethod.GET)
    @ResponseBody
    public String printBean(HttpServletRequest request, HttpServletResponse response) {

        Writer strWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        String x = null;
        try {
            Shop shop = new Shop();
            shop.setName(request.getParameter("name"));
            shop.setStaffName(new String[]{"mkyong1", "mkyong2"});
            mapper.writeValue(strWriter, shop);
            x = strWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int i=1/0;        //测试全局Exception
        return x;
    }

    @RequestMapping(value = "print", method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode pringJson(HttpServletRequest request, HttpServletResponse response) {
        ObjectNode mapper = new ObjectMapper().createObjectNode();

        mapper.put("a", "123");
        mapper.put("b", "123");
        mapper.put("c", "123");
        mapper.put("d", "123");
        return mapper;
    }
}
