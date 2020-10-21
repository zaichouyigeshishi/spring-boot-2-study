package com.example;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author hudongshan
 */
@SpringBootApplication
public class Swagger3Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Swagger3Application.class, args);
	}

	/**
	 * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		try {
			Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
			List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
			if (registrations != null) {
				for (InterceptorRegistration interceptorRegistration : registrations) {
					interceptorRegistration
							.excludePathPatterns("/swagger**/**")
							.excludePathPatterns("/webjars/**")
							.excludePathPatterns("/v3/**")
							.excludePathPatterns("/doc.html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
