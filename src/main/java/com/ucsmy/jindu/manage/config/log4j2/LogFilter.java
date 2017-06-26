package com.ucsmy.jindu.manage.config.log4j2;



import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.config.shiro.ShiroUtils;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 每次有新请求，都先获取ip地址和用户信息，记录在Log4j2上下文中
 * Created by chenqilin on 2017/5/8.
 */
public class LogFilter extends HttpServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        ThreadContext.put(LogCommUtil.MSG_IP_ADDRESS, LogCommUtil.getIpAddress());
        // ip信息存到spring session
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(LogCommUtil.MSG_IP_ADDRESS, LogCommUtil.getIpAddress());
        try {
            ShiroRealmImpl.LoginUser user = ShiroUtils.getContextUser();
            if (user != null) {
                ThreadContext.put(LogCommUtil.MSG_USER_ID, user.getId());
                ThreadContext.put(LogCommUtil.MSG_USER_NAME, user.getUserName());
            } else {
                ThreadContext.put(LogCommUtil.MSG_USER_ID, "");
                ThreadContext.put(LogCommUtil.MSG_USER_NAME, "");
            }
        } catch (Exception e) {
            ThreadContext.put(LogCommUtil.MSG_USER_ID, "");
            ThreadContext.put(LogCommUtil.MSG_USER_NAME, "");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
