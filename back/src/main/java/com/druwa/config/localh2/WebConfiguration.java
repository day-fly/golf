package com.druwa.config.localh2;

//import org.h2.server.web.WebServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;

/**
 * Allow access to h2 console from other ip addresses
 */
//@Configuration
//@Profile({"localh2"})
public class WebConfiguration {

//    @Bean
//    ServletRegistrationBean<WebServlet> h2servletRegistration() {
//        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<>(new WebServlet());
//        registrationBean.addUrlMappings("/console/*");
//        registrationBean.addUrlMappings("/h2-console/*");
//        registrationBean.addInitParameter("-webAllowOthers", "");
//        return registrationBean;
//    }
}