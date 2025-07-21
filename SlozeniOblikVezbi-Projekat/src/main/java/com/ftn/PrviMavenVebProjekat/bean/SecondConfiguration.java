package com.ftn.PrviMavenVebProjekat.bean;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class SecondConfiguration implements WebMvcConfigurer {

	@Bean(name = { "memorijaAplikacije" }, initMethod = "init", destroyMethod = "destroy")
	public ApplicationMemory getApplicationMemory() {
		return new ApplicationMemory();
	}

	@SuppressWarnings("serial")
	public class ApplicationMemory extends HashMap {

		@Override
		public String toString() {
			return "ApplicationMemory" + this.hashCode();
		}

		public void init() {
			// inicijalizacija
			System.out.println("init method called");
		}

		public void destroy() {
			// brisanje
			System.out.println("destroy method called");
		}
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver clr = new CookieLocaleResolver();
		// postavljanje default lokalizacije
		clr.setDefaultLocale(Locale.forLanguageTag("sr"));
		return clr;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages"); // base name
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang"); // use ?lang=sr
		return interceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/uploads/**")
          .addResourceLocations("file:uploads/");

        registry
          .addResourceHandler("/**")
          .addResourceLocations(
            "classpath:/META-INF/resources/", 
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/");
    }

}
