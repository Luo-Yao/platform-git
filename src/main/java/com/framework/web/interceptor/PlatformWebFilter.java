package com.framework.web.interceptor;

import com.framework.common.util.CookieUtil;
import com.framework.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-9 19:28
 * Email:johnny_lx@yahoo.com
 */
public class PlatformWebFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(PlatformWebFilter.class);

    private String[] skipPath;

    /**
     * 是否在滤过路径中
     *
     * @param path 当前url路径
     * @return true:在过滤路径中 false:不在过滤路径中
     */
    private boolean inSkipPath(String path) {
        for (int i = 0; i < skipPath.length; i++) {
            if (StringUtil.contains(path, skipPath[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.skipPath = filterConfig.getInitParameter("skipPath").split(",");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//		HttpSession session = request.getSession();
        String currPath = request.getRequestURI();
//		String paramStr = request.getQueryString()==null?"":"?"+req.getQueryString();

        logger.info("currPath===========" + currPath);
        if (skipPath != null && inSkipPath(currPath)) {
            logger.debug("在skipPath中............" + currPath);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.debug("不在skipPath中............" + currPath);

            String cookieValue = CookieUtil.getCookie(request);
            if (null == cookieValue) {
                response.sendRedirect("/framework/login.jsp");
            } else {
                String msg = CookieUtil.validateCookie(cookieValue,request,response);
                logger.debug("msg======"+msg);
                if(null!=msg&&"success".equals(msg)){
                    filterChain.doFilter(servletRequest, servletResponse);
                }else{
                    response.sendRedirect("/framework/login.jsp");
                }
            }
//          request.getRequestDispatcher("/jws/sessionTimeout.jsp").forward(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
