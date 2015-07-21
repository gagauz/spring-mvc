package org.repetitor.web.setup;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.SerializationConfig;
import org.gagauz.utils.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import freemarker.cache.URLTemplateLoader;

@Configuration
@EnableWebMvc
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = { "org.repetitor.web.controller" })
public class WebSetup extends WebMvcConfigurerAdapter {
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
		manager.addFileExtensionResolvers(resolvers);
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
				MappingJacksonJsonView view = new MappingJacksonJsonView();
				view.setPrettyPrint(true); // Lay the JSON out to be nicely
				// readable
				return view;
			}
		};
	}

	@Qualifier("viewResolver")
	@Bean
	public ViewResolver freemarkerViewResolver() {

		final freemarker.template.Configuration configuration = new
				freemarker.template.Configuration();
		configuration.setLocalizedLookup(false);
		configuration.setTemplateLoader(new URLTemplateLoader() {
			@Override
			protected URL getURL(String name) {
				try {
					return new URL("classpath:templates/" + name);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}
		});

		return new FreeMarkerViewResolver() {

			{
				setSuffix(".html");
				setOrder(1);
			}

			@Override
			public View resolveViewName(String viewName, Locale locale) throws
			Exception {
				FreeMarkerView view = new FreeMarkerView();

				view.setUrl(viewName.toLowerCase() + ".ftl");
				view.setConfiguration(configuration);
				view.setEncoding(Charset.defaultCharset().name());
				return view;
			}
		};
	}
}
