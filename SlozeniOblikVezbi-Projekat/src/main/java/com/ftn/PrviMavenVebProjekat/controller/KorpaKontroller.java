package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Karta;
import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.service.AerodromiService;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;
import com.ftn.PrviMavenVebProjekat.service.KarteService;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;

@Controller
@RequestMapping(value = "/korpa")
public class KorpaKontroller implements ApplicationContextAware {

	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private LetoviService letoviService;
	@Autowired
	private AerodromiService aerodromiService;
	@Autowired
	private AvioniService avioniService;
	@Autowired
	KarteService karteService;

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
	public ModelAndView index(@CookieValue(required = false) String karteukorpi) {
		ModelAndView modelAndView = new ModelAndView("korpa");
		if (karteukorpi.equals("") || karteukorpi.matches("R+")) {
			modelAndView.addObject("praznakorpaporuka", "Korpa je prazna!");
			return modelAndView;
		}

		ArrayList<String> karteukorpiids = new ArrayList<>(Arrays.asList(karteukorpi.split("R")));
		karteukorpiids.removeAll(Collections.singleton(null));
		karteukorpiids.removeIf(s -> s.equals(""));
		System.out.println(karteukorpiids);

		ArrayList<Karta> karteukorpilist = new ArrayList<Karta>();
		for (String id : karteukorpiids) {
			karteukorpilist.add(karteService.findOne(Long.parseLong(id)));
		}
		modelAndView.addObject("karteukorpi", karteukorpilist);
		int ukupnacenarezervacije = 0;
		for (Karta karta : karteukorpilist) {
			ukupnacenarezervacije = ukupnacenarezervacije + karta.getCena();
		}
		modelAndView.addObject("ukupnacenarezervacije", ukupnacenarezervacije);

		Long poslednjiIdKarte = karteService.findAll().get(karteService.findAll().size() - 1).getId();
		System.out.println("ID POSLEDNJE KARTE " + poslednjiIdKarte);
		modelAndView.addObject("poslednjiIdKarte", poslednjiIdKarte);

		return modelAndView;

	}

	@GetMapping(value = "/uklonistavku")
	public void uklonistavku(@RequestParam Long kartaZaIzbris, @CookieValue String karteukorpi,
			HttpServletResponse response) throws IOException {
//		String[] karteukorpiids = karteukorpi.split("R");
//		ArrayList<Karta> karteukorpilist = new ArrayList<Karta>();
//		for (String id : karteukorpiids) {
//			karteukorpilist.add(karteService.findOne(Long.parseLong(id)));
//		}
//
//		for (Karta karta : karteukorpilist) {
//			if (karta.getId() == kartaZaIzbris) {
//				karteukorpilist.remove(karta);
//			}
//		}
//
//		karteukorpiids = karteukorpilist.toString().split("R");

		Cookie cookie = new Cookie("karteukorpi", karteukorpi.replace(kartaZaIzbris.toString(), ""));
		System.out.println("KOLACIC: " + cookie.getValue());
		cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // istice za 10 godina
		cookie.setPath("/"); // Dostupan je na celom sajtu
		response.addCookie(cookie);

		response.sendRedirect(bURL + "korpa");

	}

	@PostMapping(value = "/napunikorpu")
	public ModelAndView napunikorpu(@RequestParam Long letZaRezervisati, @RequestParam ArrayList<String> imeIPrezime,
			@RequestParam ArrayList<String> brojPasosa, @RequestParam Integer brojKarata,
			@RequestParam ArrayList<String> sedista, HttpServletResponse response,
			@CookieValue(required = false) String karteukorpi) throws IOException {
//        System.out.println("NAPUNIKIORPU KONTROLLER JE AKTIVAN!");

		imeIPrezime.removeIf(ip -> ip.equals(""));
		brojPasosa.removeIf(ip -> ip.equals(""));

		if (imeIPrezime.size() != sedista.size() || brojPasosa.size() != sedista.size()) {
			ModelAndView modelAndView = new ModelAndView("rezervacija");
			modelAndView.addObject("letId", letZaRezervisati);
			modelAndView.addObject("karteBrojac", sedista.size());
			modelAndView.addObject("sedista", sedista);
			modelAndView.addObject("porukaogresci", "Morate popuniti sve podatke!");
			return modelAndView;
		}

		System.out.println("LET: " + letZaRezervisati);
		Let let = letoviService.findOne(letZaRezervisati);
		ArrayList<String> karteUKorpi = new ArrayList<String>();
		int index = 0;
		for (String sediste : sedista) {
			sediste = sediste.replace("]", "");
			sediste = sediste.replace("[", "");
			System.out.println(karteService.findAll().get(karteService.findAll().size() - 1));
			System.out.println(karteService.findAll().get(karteService.findAll().size() - 1).getId());
			Karta karta = new Karta(karteService.findAll().get(karteService.findAll().size() - 1).getId() + 1, let, sediste, imeIPrezime.get(index),
					brojPasosa.get(index));
			karteService.save(karta);
			karteUKorpi.add(karta.getId().toString());
			index++;
		}
		if (!karteukorpi.equals("")) {
			String[] karteukorpiids = karteukorpi.split("R");
			for (String id : karteukorpiids) {
				karteUKorpi.add(karteService.findOne(Long.parseLong(id)).getId().toString());

			}
		}
		try {
			Cookie cookie = new Cookie("karteukorpi", String.join("R", karteUKorpi));
			System.out.println("KOLACIC: " + cookie.getValue());
			cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // istice za 10 godina
			cookie.setPath("/"); // Dostupan je na celom sajtu
			response.addCookie(cookie);

			System.out.println("USPESNO NAPUNJENA KORPA!");

			response.sendRedirect(bURL + "korpa");
			return null;
		} catch (Exception e) {
			System.out.println("GRESKA PRILIKOM PUNJENJA KORPE :(");
			response.sendRedirect(bURL);
			return null;
		}

	}

}
