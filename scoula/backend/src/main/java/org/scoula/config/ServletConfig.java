package org.scoula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.rmi.registry.Registry;

@EnableWebMvc
@ComponentScan(basePackages = {
        "org.scoula.controller",
        "org.scoula.exception",
        "org.scoula.board.controller"
})
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/")
                .setViewName("forward:/resources/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/resources/**") // url이 /resources/로 시작하는 경로
                .addResourceLocations("/resources"); // webapp/resources/ 경로
//      "/assets/**" 패턴으로 들어오는 요청을 "/resources/assets/" 위치의 리소스 파일로 매핑
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/resources/assets/");
    }

//    jsp view resolver 설정
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry){
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/WEB-INF/views/");
//        bean.setSuffix(".jsp");
//        registry.viewResolver(bean);
//    }

    @Bean
    public MultipartResolver multipartResolver(){
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }
}