package com.hhh.fund.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hhh.util.ModelLogInterceptor;

@Configuration
@PropertySource("classpath:web.properties")
@ComponentScan(basePackages = {"com.hhh.core.controller","com.hhh.system.controller","com.hhh.web.controller","com.hhh.activiti.controller",
		"com.hhh.auth.controller","com.hhh.business.controller","com.hhh.xm.controller"},
		useDefaultFilters = false, includeFilters = {  
					@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, RestController.class})  
})  
public class ServletContext extends WebMvcConfigurationSupport {
	
	private final static Logger logger = LoggerFactory.getLogger(AppContext.class);
	
	@Autowired
	private Environment env;
	
	/**
	 * 配置HttpMessageConverter，转换成jackson
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter jacksonConverter() {
		MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
		
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jacksonConverter.setObjectMapper(om);
		
		// 避免返回Json或xml时，产生文件下载
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
		mediaTypes.add(MediaType.parseMediaType("application/json;charset=UTF-8"));
//		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

		jacksonConverter.setSupportedMediaTypes(mediaTypes);
		
		return jacksonConverter;
	}
	
	/**
	 * 拦截器bean
	 * 
	 * @return
	 */
	@Bean
	public ModelLogInterceptor localInterceptor() {
		return new ModelLogInterceptor();
	}

	/**
	 * 添加请求拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localInterceptor()).addPathPatterns("/**");
	}
	
	/**
	 *  RequestMappingHandlerMapping<br>
	 * 
	 * @return
	 */
	@Bean  
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {  
        logger.info("RequestMappingHandlerMapping");  
          
        return super.requestMappingHandlerMapping();  
    }  
	
	/**
	 * 处理静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
		registry.addResourceHandler("/views/**").addResourceLocations("/views/");
		registry.addResourceHandler("/*.html").addResourceLocations("/");
		registry.addResourceHandler("/*.js").addResourceLocations("/");
		registry.addResourceHandler("/*.css").addResourceLocations("/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}
	
	/**
	 * 文件上传 <br> 
	 * @return
	 */
	@Bean(name="multipartResolver")  
    public CommonsMultipartResolver commonsMultipartResolver() throws IOException {  
        logger.info("CommonsMultipartResolver");  
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();  
        
        commonsMultipartResolver.setMaxUploadSize(Long.parseLong(env.getProperty("maxUploadFileSize")));
        commonsMultipartResolver.setDefaultEncoding(env.getProperty("defaultEncoding"));
        commonsMultipartResolver.setUploadTempDir(new FileSystemResource(new File(env.getProperty("uploadTempDir"))));
        
        return commonsMultipartResolver;
    }  
	
	@Bean(name="restTemplate")  
	public static RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
		FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		List<HttpMessageConverter<?>> cos2 = new ArrayList<>();
		cos2.add(stringHttpMessageConverter);
		cos2.add(new ByteArrayHttpMessageConverter());
		cos2.add(new ResourceHttpMessageConverter());
		formHttpMessageConverter.setPartConverters(cos2);
		for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
			HttpMessageConverter<?> converter = restTemplate.getMessageConverters().get(i);
			if (converter instanceof FormHttpMessageConverter) {
				restTemplate.getMessageConverters().set(i, formHttpMessageConverter);
			} else if (converter instanceof StringHttpMessageConverter) {
				restTemplate.getMessageConverters().set(i, stringHttpMessageConverter);
			}
		}
		return restTemplate;
	}
}
