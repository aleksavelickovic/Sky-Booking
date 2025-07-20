package com.ftn.PrviMavenVebProjekat.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

@Controller
@RequestMapping(value="/Internacionalizacija")
public class InternacionalizacijaController {
	
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@GetMapping
	public String index(HttpServletRequest request, HttpServletResponse response) {
		
//		System.out.println("Pozivanje ispisa u različitim lokaliyacijama");
//		System.out.println(messageSource.getMessage("test1", 
//				null, Locale.forLanguageTag("sr")));
//		System.out.println(messageSource.getMessage("test2",
//				new Object[] {"Pera" , "Mika", "Žika"}, Locale.ENGLISH));
		
		Locale lokalizacija = localeResolver.resolveLocale(request);
		System.out.println(lokalizacija);
		
//		System.out.println("Pozivanje ispisa u trenutnoj lokalizaciji");
//		System.out.println(messageSource.getMessage("test1", 
//				null, lokalizacija));
//		System.out.println(messageSource.getMessage("test2",
//				new Object[] {"Pera" , "Mika", "Žika"}, lokalizacija));
		
		return "internacionalizacija";
	}	
	
	@GetMapping("/menjajLokalizacijuNaSrpski")
	public String index2(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("postavljanje lokalizacije za sesiju iz kontrolera na sr");
		localeResolver.setLocale(request, response, Locale.forLanguageTag("sr"));
		
		return "internacionalizacija";
	}	
}
