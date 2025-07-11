package com.ftn.PrviMavenVebProjekat.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
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
import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;
import com.mysql.cj.Session;

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

		ArrayList<Let> letovi = new ArrayList<Let>();
		for (Let let : service.findAll()) {
			if (let.getNaAkciji()) {
				letovi.add(let);
			}
		}
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("letovi", letovi);
		System.out.println(letovi);
		return modelAndView;

	}

	@PostMapping(value = "/filter")
	public ModelAndView filter(@RequestParam String polaziste, @RequestParam String odrediste,
			@RequestParam(required = false) String datumPolaska,
			@RequestParam(required = false) String traziSlicneLetove, @RequestParam String sortiranjePolje,
			@RequestParam String sortiranjeRedosled, @RequestParam(required = false) String oznakaleta,
			HttpSession session) {
		System.out.println(datumPolaska);
		ModelAndView modelAndView = new ModelAndView("index");
//		modelAndView.addObject("pretragaInicirana}", true);
		List<Let> sviletovi = service.findAll();

		if (polaziste != null && !polaziste.isEmpty()) {
			System.out.println("POLAZISTE nije prazno");
			modelAndView.addObject("polaziste", polaziste);
			sviletovi.removeIf(l -> !l.getPolaziste().getOznaka().equalsIgnoreCase(polaziste)
					&& !l.getPolaziste().getLokacija().getGrad().equalsIgnoreCase(polaziste)
					&& !l.getPolaziste().getLokacija().getDrzava().equalsIgnoreCase(polaziste));
		}

		if (odrediste != null && !odrediste.isEmpty()) {
			System.out.println("ODREDISTE nije prazno");
			modelAndView.addObject("odrediste", odrediste);
			sviletovi.removeIf(l -> !l.getOdrediste().getOznaka().equalsIgnoreCase(odrediste)
					&& !l.getOdrediste().getLokacija().getGrad().equalsIgnoreCase(odrediste)
					&& !l.getOdrediste().getLokacija().getDrzava().equalsIgnoreCase(odrediste));
		}

		if (!datumPolaska.equals("")) {
			System.out.println("DATUM POLASKA nije prazan");
			modelAndView.addObject("datumPolaska", datumPolaska);
			LocalDate polazak = LocalDate.parse(datumPolaska);
			System.out.println(datumPolaska);
			if (traziSlicneLetove != null) {
				System.out.println("SLICNILETOVI JE cekirano");
				modelAndView.addObject("slicnicekirano", true);
				sviletovi.removeIf(l -> !l.getTerminPolaska().toLocalDate().isEqual(polazak)
						&& !l.getTerminPolaska().toLocalDate().isEqual(polazak.plusDays(1))
						&& !l.getTerminPolaska().toLocalDate().isEqual(polazak.plusDays(2))
						&& !l.getTerminPolaska().toLocalDate().isEqual(polazak.minusDays(1))
						&& !l.getTerminPolaska().toLocalDate().isEqual(polazak.minusDays(2)));

//				sviletovi.removeIf(l -> !l.getTerminPolaska().toLocalDate().isEqual(polazak)
//						&& !l.getTerminPolaska().toLocalDate().isEqual(polazak.plusDays(2))
//						&& !l.getTerminPolaska().toLocalDate().isEqual(polazak.minusDays(2)));

			} else {
				sviletovi.removeIf(l -> !l.getTerminPolaska().toLocalDate().isEqual(polazak));
			}
		}

		if ((Korisnik) session.getAttribute(KorisniciController.KORISNIK_KEY) != null) {
			if (((Korisnik) session.getAttribute(KorisniciController.KORISNIK_KEY)).getUloga().name().equals("ADMIN")
					&& oznakaleta != null) {
				System.out.println("OZNAKALETA JE cekirano");
				modelAndView.addObject("oznakaleta", oznakaleta);

				sviletovi.removeIf(l -> !l.getOznaka().contains(oznakaleta)); // Moze i .startsWith(oznakaleta)
			}
		}

		if (!sortiranjePolje.equals("nista")) {
			Comparator<Let> comparator = null;

			switch (sortiranjePolje) {
			case "polaziste":
				comparator = Comparator
						.comparing((Let let) -> let.getPolaziste().getOznaka(), String.CASE_INSENSITIVE_ORDER)
						.thenComparing(let -> let.getPolaziste().getLokacija().getGrad(), String.CASE_INSENSITIVE_ORDER)
						.thenComparing(let -> let.getPolaziste().getLokacija().getDrzava(),
								String.CASE_INSENSITIVE_ORDER);
				break;
			case "odrediste":
				comparator = Comparator
						.comparing((Let let) -> let.getOdrediste().getOznaka(), String.CASE_INSENSITIVE_ORDER)
						.thenComparing(let -> let.getOdrediste().getLokacija().getGrad(), String.CASE_INSENSITIVE_ORDER)
						.thenComparing(let -> let.getOdrediste().getLokacija().getDrzava(),
								String.CASE_INSENSITIVE_ORDER);
				break;
			case "terminPolaska":
				comparator = Comparator.comparing(Let::getTerminPolaska);
				break;
			default:
				System.out.println("Nepoznata vrednost: " + sortiranjePolje);
				break;
			}

			if (comparator != null) {
				if (sortiranjeRedosled.equalsIgnoreCase("opadajuce")) {
					comparator = comparator.reversed();
				}
				sviletovi.sort(comparator);
			}
		}

		if (sviletovi.isEmpty()) {
			modelAndView.addObject("nemaletovaporuka", "Nema letova koji odgovaraju zadatim kriterijumima!");
		}
		System.out.println("Letovi za prikaz: " + sviletovi);
		modelAndView.addObject("letovi", sviletovi);
		return modelAndView;
	}

}
