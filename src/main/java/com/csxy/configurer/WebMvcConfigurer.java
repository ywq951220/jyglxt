package com.csxy.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}


//package com.csxy.configurer;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//
//@Configuration
//public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/templates/**")
//                .addResourceLocations("classpath:/templates/index.html")
//                .addResourceLocations("classpath:/templates/home.html")
//                .addResourceLocations("classpath:/templates/user_register.html")
//
//                .addResourceLocations("classpath:/templates/grxx/grxx_index.html")
//                .addResourceLocations("classpath:/templates/grxx/user_grjl.html")
//
//                .addResourceLocations("classpath:/templates/zpxx/zpxx_index.html")
//                .addResourceLocations("classpath:/templates/zpxx/add_zpxx.html")
//                .addResourceLocations("classpath:/templates/zpxx/add_zwxx.html")
//
//                .addResourceLocations("classpath:/templates/zwtjjl/zwtjjl_index.html")
//
//                .addResourceLocations("classpath:/templates/jltdqk/jltdqk_index.html")
//
//                .addResourceLocations("classpath:/templates/xsjl/xsjl_index.html")
//
//                .addResourceLocations("classpath:/templates/xsjyqk/xsjyqk_index.html")
//
//                .addResourceLocations("classpath:/templates/user/yhgl_index.html")
//
//                .addResourceLocations("classpath:/templates/sjtj/sjtj_index.html");
//    }
//}

