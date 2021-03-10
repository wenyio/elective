package com.example.elective.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.elective.constant.SystemConstant.USER_KEY;

/**
 * 登陆拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //静态资源不拦截
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final Object obj = request.getSession().getAttribute(USER_KEY);
        // 未登录, 跳转到登陆页面
        if (obj == null) {
            response.sendRedirect("/login");
            return false;
        } else {
            // 放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
