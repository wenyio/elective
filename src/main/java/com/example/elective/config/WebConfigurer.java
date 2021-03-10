package com.example.elective.config;

import com.example.elective.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * MVC相关配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    // 将 LoginInterceptor 注入到 WebConfigurer 中
    @Resource
    private LoginInterceptor loginInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    // 在 WebConfigurer 中的 addInterceptors 中添加拦截器，使其生效
    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    // addPathPatterns 用来设置拦截路径，excludePathPatterns 用来设置白名单，也就是不需要触发这个拦截器的路径。
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // 不拦截静态资源
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/api/user/login");
    }
}
