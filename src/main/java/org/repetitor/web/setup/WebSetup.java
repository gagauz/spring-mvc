package org.repetitor.web.setup;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.repetitor.web.controller" })
public class WebSetup extends WebMvcConfigurationSupport {

}
