package com.ftn.PrviMavenVebProjekat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Controller
@RequestMapping(value = "/")
public class LetoviController implements ApplicationContextAware {

	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private LetoviService service;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		applicationContext.getBean(ApplicationMemory.class);
	}

	@GetMapping
	public ModelAndView index() {

		List<Let> letovi = service.findAll();
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("letovi", letovi);
		System.out.println(letovi);
		return modelAndView;

	}

	@PostMapping(value = "/filter")

	public ModelAndView filter(@RequestParam String polaziste, @RequestParam String odrediste) {
		ModelAndView modelAndView = new ModelAndView("index");
		List<Let> sviletovi = service.findAll();

		if (polaziste != null && !polaziste.isEmpty()) {
			System.out.println("POLAZISTE nije prazno");
			sviletovi.removeIf(l -> !l.getPolaziste().getOznaka().equalsIgnoreCase(polaziste)
					&& !l.getPolaziste().getLokacija().getGrad().equalsIgnoreCase(polaziste)
					&& !l.getPolaziste().getLokacija().getDrzava().equalsIgnoreCase(polaziste));
		}

		if (odrediste != null && !odrediste.isEmpty()) {
			System.out.println("ODREDISTE nije prazno");
			sviletovi.removeIf(l -> !l.getOdrediste().getOznaka().equalsIgnoreCase(odrediste)
					&& !l.getOdrediste().getLokacija().getGrad().equalsIgnoreCase(odrediste)
					&& !l.getOdrediste().getLokacija().getDrzava().equalsIgnoreCase(odrediste));
		}
		
		if(sviletovi.isEmpty()) {
			modelAndView.addObject("nemaletovaporuka", "Nema letova koji odgovaraju zadatim kriterijumima!");
		}
		System.out.println("Letovi za prikaz: " + sviletovi);
		modelAndView.addObject("letovi", sviletovi);
		return modelAndView;
	}

}
