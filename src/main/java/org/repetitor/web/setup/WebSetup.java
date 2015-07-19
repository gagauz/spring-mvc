package org.repetitor.web.setup;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.SerializationConfig;
import org.gagauz.utils.C;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.repetitor.web.controller" })
public class WebSetup extends WebMvcConfigurationSupport {
    @SuppressWarnings("deprecation")
    protected List<HttpMessageConverter<?>> getMessageConverters2() {

        List<HttpMessageConverter<?>> converters = C.newArrayList();
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        converters
                .add(converter);
        converter.getObjectMapper().configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
                false);
        return converters;
    }

    @Override
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        addArgumentResolvers(argumentResolvers);

        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();
        addReturnValueHandlers(returnValueHandlers);

        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setContentNegotiationManager(mvcContentNegotiationManager());
        adapter.setMessageConverters(getMessageConverters2());
        adapter.setWebBindingInitializer(getConfigurableWebBindingInitializer());
        adapter.setCustomArgumentResolvers(argumentResolvers);
        adapter.setCustomReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

}
