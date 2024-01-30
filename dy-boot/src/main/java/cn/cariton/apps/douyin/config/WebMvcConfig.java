package cn.cariton.apps.douyin.config;

import cn.cariton.apps.douyin.interceptor.AuthCookieInterceptor;
import cn.cariton.apps.douyin.interceptor.AuthTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author icnz
 * @date 2023-12-22 12:08
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthCookieInterceptor authCookieInterceptor() {
        return new AuthCookieInterceptor();
    }

    @Bean
    public AuthTokenInterceptor authTokenInterceptor() {
        return new AuthTokenInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor1 = registry.addInterceptor(authTokenInterceptor());
        // 排除拦截配置
        // addInterceptor1.excludePathPatterns("/static/**");//排除静态资源
        // 拦截配置
        addInterceptor1.addPathPatterns("/man/**");
        InterceptorRegistration addInterceptor2 = registry.addInterceptor(authCookieInterceptor());
        // 排除拦截配置
        // addInterceptor2.excludePathPatterns("/static/**");//排除静态资源
        // 拦截配置
        addInterceptor2.addPathPatterns("/parser/**");
    }
}
