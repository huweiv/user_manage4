package com.huweiv.config;

import com.huweiv.interceptor.ProjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName LoginInterceptor
 * @Description TODO
 * @CreateTime 2022/5/17 20:41
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    ProjectInterceptor projectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> addPaths = new ArrayList<>();
        List<String> excludePaths = new ArrayList<>();
        addPaths.add("/user");
        addPaths.add("user/**");
        addPaths.add("/role");
        addPaths.add("/role/**");
        excludePaths.add("/user/login");
        excludePaths.add("/user/forgetPwd");
        excludePaths.add("/user/kaptcha");
        excludePaths.add("/pages/user/login.html");
        excludePaths.add("/pages/user/forgetPwd.html");
        excludePaths.add("/css/**");
        excludePaths.add("/js/**");
        excludePaths.add("/img/**");
        excludePaths.add("/element-ui/**");
        registry.addInterceptor(projectInterceptor).addPathPatterns(addPaths);
        registry.addInterceptor(projectInterceptor).excludePathPatterns(excludePaths);
    }
}
