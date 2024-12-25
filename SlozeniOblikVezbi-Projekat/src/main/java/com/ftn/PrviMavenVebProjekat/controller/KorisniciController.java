package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.KorisniciService;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisniciController implements ApplicationContextAware {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	@Qualifier("KorisniciDatabaseServis")
	private KorisniciService service;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
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

		List<Korisnik> korisnici = service.findAll();
		ModelAndView modelAndView = new ModelAndView("korisnici");
		modelAndView.addObject("korisnici", korisnici);
		return modelAndView;

	}

	@GetMapping(value = "/add")
	public String create() {
		return "/dodaj-korisnika.html";
	}

	@PostMapping(value = "/add")
	public void add(@RequestParam String korime, @RequestParam String lozinka, @RequestParam String lozinkaopet,
			@RequestParam String email, @RequestParam String ime, @RequestParam String prezime,
			@RequestParam Date datumrodjenja, HttpServletResponse response) throws IOException {
		if (!lozinka.equals(lozinkaopet)) {
			// TODO Privremena validacija, sa daljim razvojem projekta ce biti omoguceno
			// bolje korisnicko iskustvo
			response.sendRedirect(bURL + "ponovilozinku.html");
			return;
		}
		if (korime.equals("") || lozinka.equals("") || email.equals("") || ime.equals("") || prezime.equals("")
				|| datumrodjenja.equals("")) {
			// TODO Privremena validacija, sa daljim razvojem projekta ce biti omoguceno
			// bolje korisnicko iskustvo
			response.sendRedirect(bURL + "greska.html");
			return;
		}
		// Provera da li je korisnicko ime jedinstveno
		for (Korisnik korisnik : service.findAll()) {
			if (korisnik.getKorisnickoIme().equals(korime)) {
				response.sendRedirect(bURL + "korimepostoji.html");
				return;
			}
		}
		// Provera da li je unesen email jedinstven
		for (Korisnik korisnik : service.findAll()) {
			if (korisnik.getEmail().equals(email)) {
				response.sendRedirect(bURL + "korimepostoji.html");
				return;
			}
		}
		service.save(new Korisnik(korime, lozinka, email, ime, prezime, datumrodjenja,
				Timestamp.valueOf(LocalDateTime.now().withNano(0)), Uloga.PUTNIK));
		response.sendRedirect(bURL + "korisnici");
		return;
	}

}
