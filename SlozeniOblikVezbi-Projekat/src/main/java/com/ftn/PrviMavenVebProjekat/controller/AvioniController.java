package com.ftn.PrviMavenVebProjekat.controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Avion;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;
import com.ftn.PrviMavenVebProjekat.service.KorisniciService;

@Controller
@RequestMapping(value = "/avioni")
public class AvioniController implements ApplicationContextAware {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	private AvioniService service;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		applicationContext.getBean(ApplicationMemory.class);
	}

	@GetMapping(value = "/add")
	public ModelAndView addAvion() {
		ModelAndView modelAndView = new ModelAndView("dodaj-avion");
		return modelAndView;
	}

	@PostMapping(value = "/add")
	public ModelAndView addAvion(@RequestParam(required = false) String naziv,
			@RequestParam(required = false) Integer brojKolona, @RequestParam(required = false) Integer brojRedova) {
		ModelAndView modelAndView = new ModelAndView("dodaj-avion");
		ArrayList<Avion> avioni = (ArrayList<Avion>) service.findAll();
		if (brojKolona == null || brojRedova == 0) {
			modelAndView.addObject("poruka", "Molimo Vas unesite sve parametre!");
			return modelAndView;
		}

		if (naziv.isBlank() || brojKolona == 0 || brojRedova == 0) {
			modelAndView.addObject("poruka", "Molimo Vas unesite sve parametre!");
			return modelAndView;
		}

		for (Avion avion : avioni) {
			if (avion.getNaziv().equalsIgnoreCase(naziv)) {
				modelAndView.addObject("poruka", "Vec postoji avion sa ovim nazivom!");
				return modelAndView;
			}
		}
		service.save(new Avion(naziv, brojKolona, brojRedova));
		modelAndView.addObject("uspeh", "Uspesno ste dodali avion!");

		return modelAndView;

	}
}
