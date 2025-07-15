package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.sql.Timestamp;
import java.text.CollationKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Aerodrom;
import com.ftn.PrviMavenVebProjekat.model.Avion;
import com.ftn.PrviMavenVebProjekat.model.Karta;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.model.Rezervacija;
import com.ftn.PrviMavenVebProjekat.repository.impl.RezervacijeRepositoryImpl;
import com.ftn.PrviMavenVebProjekat.service.AerodromiService;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;
import com.ftn.PrviMavenVebProjekat.service.KarteService;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;
import com.ftn.PrviMavenVebProjekat.service.RezervacijeService;
import com.mysql.cj.Session;
import com.mysql.cj.x.protobuf.MysqlxExpr.ColumnIdentifier;

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
	@Autowired
	private AerodromiService aerodromiService;
	@Autowired
	private AvioniService avioniService;
	@Autowired
	private KarteService karteService;
	@Autowired
	private RezervacijeService rezervacijeService;
	@Autowired
	private RezervacijeRepositoryImpl rezervacijeRepository;
	private int setovanCookie = 0;

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
	public ModelAndView index(HttpServletResponse response) {

		if (setovanCookie == 0) {
			Cookie cookie = new Cookie("karteukorpi", "");
			cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // istice za 10 godina
			cookie.setPath("/"); // Dostupan je na celom sajtu
			response.addCookie(cookie);
			setovanCookie = 1;
		}

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

	@GetMapping(value = "/letovi/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("dodaj-let");
		List<Aerodrom> aerodrmi = aerodromiService.findAll();
		List<Avion> avioni = avioniService.findAll();

		modelAndView.addObject("aerodromi", aerodrmi);
		modelAndView.addObject("avioni", avioni);

		return modelAndView;
	}

	@PostMapping(value = "/letovi/add")
	public ModelAndView add(@RequestParam(required = false) String oznaka,
			@RequestParam(required = false) String polazak, @RequestParam(required = false) Integer trajanje,
			@RequestParam(required = false) Integer cena, @RequestParam(required = false) Long polaziste,
			@RequestParam(required = false) Long odrediste, @RequestParam(required = false) Long avion) {
		ModelAndView modelAndView = new ModelAndView("dodaj-let");
		List<Aerodrom> aerodrmi = aerodromiService.findAll();
		List<Avion> avioni = avioniService.findAll();

		modelAndView.addObject("aerodromi", aerodrmi);
		modelAndView.addObject("avioni", avioni);

		if (polazak.isBlank() || oznaka == null || oznaka.isBlank() || polazak == null || trajanje == null
				|| trajanje <= 0 || cena == null || cena <= 0 || polaziste == -1 || odrediste == -1 || avion == -1) {
			modelAndView.addObject("poruka",
					"Sva polja moraju biti popunjena, trajanje i cena moraju biti pozitivni brojevi!");
			return modelAndView;
		}

		if (!oznaka.startsWith("FL")) {
			modelAndView.addObject("poruka", "Oznaka mora pocinjati sa FL!");
			return modelAndView;
		}

		if (polaziste == odrediste) {
			modelAndView.addObject("poruka", "Polazni i odredisni aerodrom moraju biti razliciti!");
			return modelAndView;
		}

		for (Let let : service.findAll()) {
			if (let.getOznaka().equalsIgnoreCase(oznaka)) {
				modelAndView.addObject("poruka", "Ova oznaka vec postoji!");
				return modelAndView;
			}
		}

		Let let = new Let(oznaka.toUpperCase(), aerodromiService.findOne(polaziste),
				aerodromiService.findOne(odrediste), avioniService.findOne(avion), LocalDateTime.parse(polazak),
				trajanje, cena, false);
		service.save(let);
		modelAndView.addObject("uspeh", true);
		modelAndView.addObject("uspehporuka", "Uspesno ste dodali novi let!");

		return modelAndView;

	}

	@GetMapping(value = "/letovi/edit")
	public ModelAndView edit(@RequestParam Long letId) {
		ModelAndView modelAndView = new ModelAndView("izmeni-let");
		Let letZaEdit = service.findOne(letId);

		List<Aerodrom> aerodrmi = aerodromiService.findAll();
		List<Avion> avioni = avioniService.findAll();

		modelAndView.addObject("aerodromi", aerodrmi);
		modelAndView.addObject("avioni", avioni);

		modelAndView.addObject("let", letZaEdit);

		return modelAndView;

	}

	@PostMapping(value = "/letovi/edit")
	public ModelAndView edit(@RequestParam Long id, @RequestParam(required = false) String oznaka,
			@RequestParam(required = false) String polazak, @RequestParam(required = false) Integer trajanje,
			@RequestParam(required = false) Integer cena, @RequestParam(required = false) Long polaziste,
			@RequestParam(required = false) Long odrediste, @RequestParam(required = false) Long avion,
			@RequestParam(required = false) Boolean naAkciji, @RequestParam(required = false) Integer brojMesta) {
		ModelAndView modelAndView = new ModelAndView("izmeni-let");
//		Let letZaEdit = service.findOne(letId);

		List<Aerodrom> aerodrmi = aerodromiService.findAll();
		List<Avion> avioni = avioniService.findAll();

		modelAndView.addObject("aerodromi", aerodrmi);
		modelAndView.addObject("avioni", avioni);

		if (polazak.isBlank() || oznaka == null || oznaka.isBlank() || polazak == null || trajanje == null
				|| trajanje <= 0 || cena == null || cena <= 0 || polaziste == -1 || odrediste == -1 || avion == -1) {
			modelAndView.addObject("poruka",
					"Sva polja moraju biti popunjena, trajanje i cena moraju biti pozitivni brojevi!");
			Let letEdited = service.findOne(id);
			modelAndView.addObject("let", letEdited);
			return modelAndView;
		}

		if (!oznaka.startsWith("FL")) {
			modelAndView.addObject("poruka", "Oznaka mora pocinjati sa FL!");
			Let letEdited = service.findOne(id);
			modelAndView.addObject("let", letEdited);
			return modelAndView;
		}

		if (polaziste == odrediste) {
			modelAndView.addObject("poruka", "Polazni i odredisni aerodrom moraju biti razliciti!");
			Let letEdited = service.findOne(id);
			modelAndView.addObject("let", letEdited);
			return modelAndView;
		}

		for (Let let : service.findAll()) {
			if (let.getOznaka().equalsIgnoreCase(oznaka)) {
				modelAndView.addObject("poruka", "Ova oznaka vec postoji!");
				Let letEdited = service.findOne(id);
				modelAndView.addObject("let", letEdited);
				return modelAndView;
			}
		}

		Let letEdited = new Let(id, oznaka.toUpperCase(), aerodromiService.findOne(polaziste),
				aerodromiService.findOne(odrediste), avioniService.findOne(avion), LocalDateTime.parse(polazak),
				trajanje, cena, naAkciji, brojMesta);
		modelAndView.addObject("let", letEdited);

		letEdited.setOznaka(letEdited.getOznaka().toUpperCase());
		service.update(letEdited);
		modelAndView.addObject("uspeh", true);
		modelAndView.addObject("uspehporuka", "Uspesno ste izmenili let!");

		return modelAndView;

	}

	@GetMapping(value = "/seatoptions")
	public ModelAndView izbor(HttpServletResponse response, HttpSession session, @RequestParam Long letId)
			throws IOException {
		ModelAndView modelAndView = new ModelAndView("izbor-sedista");
		modelAndView.addObject("letId", letId);

		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute(KorisniciController.KORISNIK_KEY);
		if (ulogovaniKorisnik == null) {
			response.sendRedirect(bURL + "korisnici/login?letZaRezervsati=" + letId);
			return null;
		}

		Let let = service.findOne(letId);
		Avion avion = let.getAvion();
		modelAndView.addObject("kolone", avion.getBrojKolona());
		modelAndView.addObject("redovi", avion.getBrojRedova());

		return modelAndView;

	}

	@GetMapping(value = "/reservation")
	public ModelAndView reservation(HttpServletResponse response, HttpSession session, @RequestParam Long letId,
			@RequestParam List<String> sedista) throws IOException {
		ModelAndView modelAndView = new ModelAndView("rezervacija");

//
//		ArrayList<Integer> karteBrojac = new ArrayList<Integer>();
//		for (int i = 1; i <= brojMesta; i++) {
//			karteBrojac.add(i);
//		}
		modelAndView.addObject("letId", letId);
		modelAndView.addObject("karteBrojac", sedista.size());
		modelAndView.addObject("sedista", sedista);
		return modelAndView;
	}

	@PostMapping(value = "/reservation")
	public void reservation(@CookieValue String karteukorpi, HttpSession session, HttpServletResponse response)
			throws IOException {
//		ModelAndView modelAndView = new ModelAndView("korpa");
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute(KorisniciController.KORISNIK_KEY);
		String[] karteukorpiids = karteukorpi.split("R");
		ArrayList<Karta> karteukorpilist = new ArrayList<Karta>();
		for (String id : karteukorpiids) {
			karteukorpilist.add(karteService.findOne(Long.parseLong(id)));
		}
		int ukupnacenarezervacije = 0;
		System.out.println("KARTICE U KORPICI:" + karteukorpilist);
		for (Karta karta : karteukorpilist) {
			ukupnacenarezervacije = ukupnacenarezervacije + karta.getCena();
			System.out.println("UKUPnA CENA rezervacije: " + ukupnacenarezervacije);
		}

		Rezervacija rezervacija = new Rezervacija(ulogovaniKorisnik, ukupnacenarezervacije);
		rezervacijeService.save(rezervacija);

		System.out.println("KARTICE U KORPICI 2:" + karteukorpilist);
		for (Karta karta : karteukorpilist) {
			rezervacijeRepository.addKartaToRezervacija(rezervacijeService.findAll().getLast().getId(), karta.getId());
		}
		Cookie cookie = new Cookie("karteukorpi", "");
		cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // istice za 10 godina
		cookie.setPath("/"); // Dostupan je na celom sajtu
		response.addCookie(cookie);

		System.out.println("REZERVACIJA USPESNO KOMPLETIRANA!");

		response.sendRedirect(bURL);

	}

	@PostMapping(value = "/filter")
	public ModelAndView filter(@RequestParam String polaziste, @RequestParam String odrediste,
			@RequestParam(required = false) String datumPolaska,
			@RequestParam(required = false) String traziSlicneLetove, @RequestParam String sortiranjePolje,
			@RequestParam String sortiranjeRedosled, @RequestParam(required = false) String oznakaleta,
			Integer brojMesta, HttpSession session) {
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

		if (brojMesta != null) {
			System.out.println("Broj mesta nije prazan!");
			modelAndView.addObject("brojMesta", brojMesta);
			sviletovi.removeIf(l -> l.getBrojMesta() < brojMesta);
		}

		if (!datumPolaska.equals("")) {
			System.out.println("DATUM POLASKA nije prazan");
			modelAndView.addObject("datumPolaska", datumPolaska);
			LocalDate polazak = LocalDate.parse(datumPolaska);
			System.out.println(datumPolaska);
			if (traziSlicneLetove != null) {
				System.out.println("SLICNILETOVI JE cekirano");
				System.out.println(traziSlicneLetove);
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
