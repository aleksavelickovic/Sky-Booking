package com.ftn.PrviMavenVebProjekat.controller;

import java.util.List;

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
import com.ftn.PrviMavenVebProjekat.model.Aerodrom;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.service.AerodromiService;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Controller
@RequestMapping(value = "/aerodromi")
public class AerodromiController implements ApplicationContextAware {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	private AerodromiService service;
	@Autowired
	@Qualifier("LokacijeDatabaseServis")
	private LokacijaService lokacijeService;

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
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("dodaj-aerodrom");
		List<Lokacija> lokacije = lokacijeService.findAll();
		modelAndView.addObject("lokacije", lokacije);

		return modelAndView;

	}

	@PostMapping(value = "/add")
	public ModelAndView add(@RequestParam(required = false) String oznaka,
			@RequestParam(required = false) Long lokacijaID) {
		ModelAndView modelAndView = new ModelAndView("dodaj-aerodrom");
		List<Lokacija> lokacije = lokacijeService.findAll();
		modelAndView.addObject("lokacije", lokacije);

		if (lokacijaID == null) {
			System.out.println("LOKACIJAID JE NULL");
		}

		if (oznaka.isBlank() || lokacijaID == null || lokacijaID == -1) {
//			System.out.println("PRVI IF");
			modelAndView.addObject("poruka", "Morate popuniti sva polja!");
			return modelAndView;
		}

		if (oznaka.length() != 3) {
			modelAndView.addObject("poruka", "Oznaka mora imati tacno 3 slova!");
			return modelAndView;
		}

		for (Aerodrom aerodrom : service.findAll()) {
			if (aerodrom.getOznaka().equalsIgnoreCase(oznaka)) {
				modelAndView.addObject("poruka", "Ova oznaka vec postoji!");
				return modelAndView;
			}
		}

		service.save(new Aerodrom(oznaka.toUpperCase(), lokacijeService.findOne(lokacijaID)));
		modelAndView.addObject("uspeh", "Uspesno ste dodali Aerodrom!");

		return modelAndView;

	}

}
