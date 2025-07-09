package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Kontinenti;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Controller
@RequestMapping(value = "/lokacije")
public class LokacijeController implements ApplicationContextAware {

	public static final String LOKACIJE_KEY = "lokacije";

	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier("LokacijeDatabaseServis")
	private LokacijaService service;

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
	@ResponseBody
	public ModelAndView index() {

		List<Lokacija> lokacije = service.findAll();
		ModelAndView modelAndView = new ModelAndView("lokacije");
		modelAndView.addObject("lokacije", lokacije);
		return modelAndView;

	}

	@GetMapping(value = "/add")
	public String create() {
		return "dodaj-lokaciju";
	}

	@PostMapping(value = "/add")
	public void create(@RequestParam String grad, @RequestParam String drzava, @RequestParam Kontinenti kontinent,
			HttpServletResponse response) throws IOException {
		if (grad.equals("") || drzava.equals("")) {
			response.sendRedirect(bURL + "greska.html");
			return;
		}
		service.save(new Lokacija(grad, drzava, kontinent));
		response.sendRedirect(bURL + "lokacije");
		return;

	}

	@GetMapping(value = "/edit")
	@ResponseBody
	public ModelAndView edit(@RequestParam Long id) {

		Lokacija lokacijaZaEdit = service.findOne(id);
		ModelAndView modelAndView = new ModelAndView("izmeni-lokaciju");
		modelAndView.addObject("lokacija", lokacijaZaEdit);
		return modelAndView;
	}

	@PostMapping(value = "/edit")
	public void edit(@ModelAttribute Lokacija lokacijaEdited, HttpServletResponse response) throws IOException {
		if (lokacijaEdited.getDrzava().equals("") || lokacijaEdited.getGrad().equals("")) {
			response.sendRedirect(bURL + "greska.html");
			return;
		}
		service.update(lokacijaEdited);
		response.sendRedirect(bURL + "lokacije");
	}

	@GetMapping(value = "/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		service.delete(id);
		response.sendRedirect(bURL + "lokacije");
	}

	@GetMapping(value = "/details")
	@ResponseBody
	public void details(@RequestParam Long id) {
		return;
	}
}
