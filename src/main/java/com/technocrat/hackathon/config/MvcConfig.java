package com.technocrat.hackathon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technocrat.hackathon.util.CustomErrorResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.inject.Inject;

/**
 * Created by vikas on 04-04-2020.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public CustomErrorResponseHandler customErrorResponseHandler;

    @Inject
    public void setCustomErrorResponseHandler(CustomErrorResponseHandler customErrorResponseHandler) {
        this.customErrorResponseHandler = customErrorResponseHandler;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate r = new RestTemplate();
        r.setErrorHandler(customErrorResponseHandler);
        return r;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
