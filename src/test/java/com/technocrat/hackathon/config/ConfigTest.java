package com.technocrat.hackathon.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vikas on 04-04-2020.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.technocrat.hackathon.*")
public class ConfigTest {
}
