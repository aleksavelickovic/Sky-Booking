package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.sql.Timestamp;
import java.text.CollationKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Aerodrom;
import com.ftn.PrviMavenVebProjekat.model.Avion;
import com.ftn.PrviMavenVebProjekat.model.Karta;
import com.ftn.PrviMavenVebProjekat.model.Kontinenti;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.model.Rezervacija;
import com.ftn.PrviMavenVebProjekat.repository.impl.RezervacijeRepositoryImpl;
import com.ftn.PrviMavenVebProjekat.service.AerodromiService;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;
import com.ftn.PrviMavenVebProjekat.service.KarteService;
import com.ftn.PrviMavenVebProjekat.service.KorisniciService;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;
import com.ftn.PrviMavenVebProjekat.service.RezervacijeService;
import com.google.gson.Gson;
import com.mysql.cj.Session;
import com.mysql.cj.x.protobuf.MysqlxExpr.ColumnIdentifier;

import ch.qos.logback.classic.spi.ILoggingEvent;

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
	@Autowired
	private KorisniciService korisniciService;
	@Autowired
	ObjectMapper mapper;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		applicationContext.getBean(ApplicationMemory.class);

		mapper.registerModule(new JavaTimeModule());

	}

	@GetMapping
	public ModelAndView index(HttpServletResponse response, HttpSession session) {
		Integer setovanCookie = (Integer) session.getAttribute("setovanCookie");

		if (setovanCookie == null) {
			Cookie cookie = new Cookie("karteukorpi", "");
			cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // istice za 10 godina
			cookie.setPath("/"); // Dostupan je na celom sajtu
			response.addCookie(cookie);
			session.setAttribute("setovanCookie", 1);
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

		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute(KorisniciController.KORISNIK_KEY);
		if (ulogovanKorisnik != null) {
			for (Rezervacija rezervacija : rezervacijeService.findAll()) {
				if (rezervacija.getKorisnik().getId().equals(ulogovanKorisnik.getId())) {
					for (Karta karta : rezervacija.getKarte()) {
						if (!karta.getLet().getRazlogOtkaza().equals("")
								&& karta.getLet().getTerminPolaska().isAfter(LocalDateTime.now())) {
							modelAndView.addObject("razlogOtkaza", "Let " + karta.getLet().getOznaka()
									+ " je otkazan, ukoliko imate Loyalty Karticu, kao kompenzaciju dobili ste 5 loyalty bodova\n"
									+ System.lineSeparator() + "\nRazlog otkaza: " + karta.getLet().getRazlogOtkaza());
						}
					}
				}
			}
		}

		return modelAndView;

	}

	@PostMapping(value = "/letovi/definisiakciju", produces = "application/json")
	@ResponseBody
	public String definisiakciju(@RequestParam(required = false) String procenatpopusta, @RequestParam Long letId,
			@RequestParam String datumVazenjaAkcije) throws JsonProcessingException {
		System.out.println("Poruka stigla do servera, procenat popusta: " + procenatpopusta + " LetId: " + letId);
		Let let = service.findOne(letId);
//		ObjectMapper objectMapper = new ObjectMapper();
//		Gson gson = new Gson();
		if (procenatpopusta.equals("") || procenatpopusta.equals(null)) {
			Let letzavratiti = new Let(-1L, "P", new Aerodrom(-1L, "", new Lokacija("", "", Kontinenti.Afrika)),
					new Aerodrom(-2L, "", new Lokacija("", "", Kontinenti.Afrika)), new Avion("", 1, 1),
					LocalDateTime.now(), 0, 0, false, 1, "", LocalDate.EPOCH, 2);
			return mapper.writeValueAsString(letzavratiti);
		}
		if (let.getNaAkciji()) {
			Let letzavratiti = new Let(-1L, "", new Aerodrom(-1L, "", new Lokacija("", "", Kontinenti.Afrika)),
					new Aerodrom(-2L, "", new Lokacija("", "", Kontinenti.Afrika)), new Avion("", 1, 1),
					LocalDateTime.now(), 0, 0, false, 1, "", LocalDate.EPOCH, 2);
			return mapper.writeValueAsString(letzavratiti);
		}
		let.setStaraCena(let.getCena());
		let.setNaAkciji(true);

		let.setCena((int) Math.ceil(let.getCena() * (1 - (Double.valueOf(procenatpopusta) / 100))));

		System.out.println("NOVA AKCIJSKA CENA LETA: " + let.getCena());
		let.setDatumVazenjaAkcije(LocalDate.parse(datumVazenjaAkcije));
		System.out.println("DATUMVAZENJA AKCIJE: " + let.getDatumVazenjaAkcije());
		service.update(let);

		return mapper.writeValueAsString(let);
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
			@RequestParam(required = false) Boolean naAkciji, @RequestParam(required = false) Integer brojMesta,
			@RequestParam(required = false) String razlogOtkaza,
			@RequestParam(required = false) String datumVazenjaAkcije, @RequestParam(required = false) int staraCena) {
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
				trajanje, cena, naAkciji, brojMesta, razlogOtkaza, LocalDate.parse(datumVazenjaAkcije), staraCena);
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

		ArrayList<String> rezervisanaSedista = new ArrayList<String>();
		for (Rezervacija rezervacija : rezervacijeService.findAll()) {
			for (Karta karta : rezervacija.getKarte()) {
				if (karta.getLet().getId() == letId) {
					rezervisanaSedista.add(karta.getBrojSedista());
				}
			}
		}

		modelAndView.addObject("rezervisanaSedista", rezervisanaSedista);

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
	public void reservation(@CookieValue String karteukorpi, HttpSession session, HttpServletResponse response,
			@RequestParam(required = false) Integer loyaltyBodovi) throws IOException {
		System.out.println("Korisnik želi da iskoristi bodova: " + loyaltyBodovi);

//		ModelAndView modelAndView = new ModelAndView("korpa");
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute(KorisniciController.KORISNIK_KEY);
		ArrayList<String> karteukorpiids = new ArrayList<>(Arrays.asList(karteukorpi.split("R")));
		karteukorpiids.removeAll(Collections.singleton(null));
		karteukorpiids.removeIf(s -> s.equals(""));
		System.out.println(karteukorpiids);
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

		try {
			if (ulogovaniKorisnik.getLoyaltyBodovi() >= 0) {
				double procenatPopusta = loyaltyBodovi * 0.07;

				if (procenatPopusta > 1.0) {
					procenatPopusta = 1.0;
				}

				ukupnacenarezervacije = (int) Math
						.ceil(ukupnacenarezervacije - (ukupnacenarezervacije * procenatPopusta));

				// TODO napravi da mu napise kolko je bodova skinuto

				ulogovaniKorisnik.setLoyaltyBodovi(ulogovaniKorisnik.getLoyaltyBodovi() - loyaltyBodovi);
				session.setAttribute(KorisniciController.KORISNIK_KEY, ulogovaniKorisnik);
			}
		} catch (Exception e) {
			// TODO: handle exception

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

		Rezervacija rezervacija2 = rezervacijeService.findAll().getLast();

		Map<Let, Long> letoviSaBrojemKarata = rezervacija2.getKarte().stream()
				.collect(Collectors.groupingBy(Karta::getLet, Collectors.counting()));

		for (Map.Entry<Let, Long> entry : letoviSaBrojemKarata.entrySet()) {
			Let letFromMap = entry.getKey();
			long brojKarata = entry.getValue();

			// Ucitavanje poslednjeg leta iz baze
			Let let = service.findOne(letFromMap.getId());

			int novaBrojMesta = let.getBrojMesta() - (int) brojKarata;

			let.setBrojMesta(novaBrojMesta);
			service.update(let);
		}

		ulogovaniKorisnik.setParaPotroseno(ulogovaniKorisnik.getParaPotroseno() + ukupnacenarezervacije);
		korisniciService.update(ulogovaniKorisnik);

		try {
			if (ulogovaniKorisnik.getLoyaltyBodovi() >= 0) {
				ulogovaniKorisnik.setLoyaltyBodovi(
						ulogovaniKorisnik.getLoyaltyBodovi() + (int) Math.floor(ukupnacenarezervacije / 30000));
				// TODO napravi da mu napise kolko je bodova dobio
				korisniciService.update(ulogovaniKorisnik);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		System.out.println("REZERVACIJA USPESNO KOMPLETIRANA!");

		response.sendRedirect(bURL);

	}

	@GetMapping(value = "/otkazi")
	public ModelAndView otkazilet(@RequestParam Long letId, HttpServletResponse response,
			@RequestParam String razlogOtkaza) throws IOException {
		ModelAndView modelAndView = new ModelAndView("index");
		Let letZaOtkazivanje = service.findOne(letId);
		List<Let> letovi = service.findAll();
		modelAndView.addObject("letovi", letovi);

		if (letZaOtkazivanje.getTerminPolaska().minusHours(1).isBefore(LocalDateTime.now())) {
			modelAndView.addObject("otkazgreska", "Let je za manje od sat vremena, nemoguce je otkazati ga!");
			return modelAndView;
		}

		for (Rezervacija rezervacija : rezervacijeService.findAll()) {
			System.out.println("PETLJA ZA REZERVACIJE");
			for (Karta karta : rezervacija.getKarte()) {
				System.out.println("PETLJA ZA KARTE");
				if (karta.getLet().getId() == letId) {
					System.out.println("PETLJA ZA LETOVE");
					Korisnik korisnik = rezervacija.getKorisnik();
					if (korisnik.getLoyaltyBodovi() >= 0) {
						korisnik.setLoyaltyBodovi(korisnik.getLoyaltyBodovi() + 5);
						System.out.println("PETLJA ZA DODELU BODOVA");
						korisniciService.update(korisnik);
					}

//					break;
				}
			}
		}
		letZaOtkazivanje.setRazlogOtkaza(razlogOtkaza);
		service.update(letZaOtkazivanje);

		modelAndView.addObject("otkazuspeh",
				"Uspesno ste otkazali let, svim ostecenim strankama je dodeljeno 5 loyalty bodova");
		return modelAndView;
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
