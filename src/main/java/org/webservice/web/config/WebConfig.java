package org.webservice.web.config;

import freemarker.cache.URLTemplateLoader;
import freemarker.ext.jsp.TaglibFactory;
import org.gagauz.utils.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.webservice.web.controller.Index;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = {Index.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    protected List<HttpMessageConverter<?>> getMessageConverters2() {

        List<HttpMessageConverter<?>> converters = C.newArrayList();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converters
                .add(converter);
        //        converter.getObjectMapper().configure(MapperFeature.FAIL_ON_EMPTY_BEANS, false);
        return converters;
    }

    // @Override
    // @Bean
    // public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    // List<HandlerMethodArgumentResolver> argumentResolvers = new
    // ArrayList<HandlerMethodArgumentResolver>();
    // addArgumentResolvers(argumentResolvers);
    //
    // List<HandlerMethodReturnValueHandler> returnValueHandlers = new
    // ArrayList<HandlerMethodReturnValueHandler>();
    // addReturnValueHandlers(returnValueHandlers);
    //
    // RequestMappingHandlerAdapter adapter = new
    // RequestMappingHandlerAdapter();
    // adapter.setContentNegotiationManager(mvcContentNegotiationManager());
    // adapter.setMessageConverters(getMessageConverters2());
    // adapter.setWebBindingInitializer(getConfigurableWebBindingInitializer());
    // adapter.setCustomArgumentResolvers(argumentResolvers);
    // adapter.setCustomReturnValueHandlers(returnValueHandlers);
    //
    // return adapter;
    // }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .useJaf(false)
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("csv", MediaType.TEXT_PLAIN);
    }

    @Bean
    @Autowired
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager,
                                                       @Qualifier("viewResolver") ViewResolver[] resolvers) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        List<ViewResolver> viewResolvers = Arrays.asList(resolvers);
        resolver.setViewResolvers(viewResolvers);
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }

    @Qualifier("viewResolver")
    @Bean
    public ViewResolver jsonViewResolver() {
        return new ViewResolver() {
            @Override
            public View resolveViewName(String viewName, Locale locale) throws
                    Exception {
                MappingJackson2JsonView view = new MappingJackson2JsonView();
                view.setPrettyPrint(true); // Lay the JSON out to be nicely
                // readable
                return view;
            }
        };
    }

    @Bean
    public FreeMarkerConfig buildFreeMarkerConfig(final WebApplicationContext ctx) {

        final freemarker.template.Configuration configuration = new
                freemarker.template.Configuration();
        configuration.setLocalizedLookup(false);
        configuration.setTemplateLoader(new URLTemplateLoader() {
            @Override
            protected URL getURL(String name) {
                return getClass().getResource("/" + name);
            }
        });

        return new FreeMarkerConfig() {

            @Override
            public TaglibFactory getTaglibFactory() {
                return new TaglibFactory(ctx.getServletContext());
            }

            @Override
            public freemarker.template.Configuration getConfiguration() {
                return configuration;
            }
        };
    }

    @Qualifier("viewResolver")
    @Bean
    public ViewResolver freemarkerViewResolver(final WebApplicationContext ctx) {

        return new FreeMarkerViewResolver() {

            {
                setSuffix(".html");
                setOrder(1);
                setServletContext(ctx.getServletContext());
                setApplicationContext(ctx);
            }

            @Override
            public View resolveViewName(String viewName, Locale locale) throws
                    Exception {
                FreeMarkerView view = new FreeMarkerView();
                view.setUrl(viewName.toLowerCase() + ".ftl");
                view.setEncoding(Charset.defaultCharset().name());
                view.setApplicationContext(getApplicationContext());
                view.setServletContext(getServletContext());
                return view;
            }
        };
    }
}
