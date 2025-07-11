package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	public static final String KORISNIK_KEY = "prijavljeniKorisnik";

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
//	@ResponseBody
	public ModelAndView index() {

		List<Korisnik> korisnici = service.findAll();
		ModelAndView modelAndView = new ModelAndView("korisnici");
		modelAndView.addObject("korisnici", korisnici);
		return modelAndView;

	}

	@GetMapping(value = "/add")
	public String create() {
		return "dodaj-korisnika";
	}

	@PostMapping(value = "/add")
	public ModelAndView add(@RequestParam String korime, @RequestParam String lozinka, @RequestParam String lozinkaopet,
			@RequestParam String email, @RequestParam String ime, @RequestParam String prezime,
			@RequestParam String datumrodjenja, HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = new ModelAndView("dodaj-korisnika");
		if (!lozinka.equals(lozinkaopet)) {
			modelAndView.addObject("poruka", "Lozinke se ne poklapaju!");
			return modelAndView;
		}
		if (korime.equals("") || lozinka.equals("") || email.equals("") || ime.equals("") || prezime.equals("")
				|| datumrodjenja == null) { // TODO validacija za datum ne radi!
			modelAndView.addObject("poruka", "Morate popuniti sva polja!");
			return modelAndView;
		}
		// Provera da li je korisnicko ime jedinstveno
		for (Korisnik korisnik : service.findAll()) {
			if (korisnik.getKorisnickoIme().equals(korime)) {
//				response.sendRedirect(bURL + "korimepostoji.html");
				modelAndView.addObject("poruka", "Korisnicko ime vec postoji!");
				return modelAndView;
			}
		}
		// Provera da li je unesen email jedinstven
		for (Korisnik korisnik : service.findAll()) {
			if (korisnik.getEmail().equals(email)) {
				modelAndView.addObject("poruka", "Email vec postoji!");
				return modelAndView;
			}
		}
		service.save(new Korisnik(korime, lozinka, email, ime, prezime, Date.valueOf(datumrodjenja), // TODO validacija
																										// za datum ne
																										// radi!
				Timestamp.valueOf(LocalDateTime.now().withNano(0)), Uloga.PUTNIK));
		response.sendRedirect(bURL + "korisnici/login");
		return null;
	}

	@GetMapping(value = "/blockunblock")
	public void blockunblock(@RequestParam Long id, HttpServletResponse response) throws IOException {
		Korisnik korisnik = service.findOne(id);
		// Nepotrebno zato sto je na frontu dugme disabled ako je korisnik admin
//		if (korisnik.getUloga() == Uloga.ADMIN) {
//			response.sendRedirect(bURL + "korisnici");
//			return;
//		}
		if (korisnik.getBlokiran() == true) {

			korisnik.setBlokiran(false);
			service.blockunblock(korisnik);
			response.sendRedirect(bURL + "korisnici#" + korisnik.getId());
			return;

		} else if (korisnik.getBlokiran() == false) {
			korisnik.setBlokiran(true);
			service.blockunblock(korisnik);
			response.sendRedirect(bURL + "korisnici#" + korisnik.getId());
			return;
		}
		return;
	}

	@GetMapping(value = "/login")
	public ModelAndView login() throws IOException {
		ModelAndView modelAndView = new ModelAndView("prijava");
//		modelAndView.addObject("poruka", "");
		return modelAndView;
	}

	@PostMapping(value = "/login")
	public ModelAndView login(@RequestParam String korisnickoIme, @RequestParam String lozinka, HttpSession session,
			HttpServletResponse response) throws IOException {
		ModelAndView rezultat = new ModelAndView("prijava");
		for (Korisnik korisnik : service.findAll()) {
			if (korisnik.getKorisnickoIme().equals(korisnickoIme) && korisnik.getLozinka().equals(lozinka)) {
				if (korisnik.getBlokiran()) {
					rezultat.addObject("poruka", "Ovaj nalog je blokiran od strane administratora!");
					System.out.println("KORISNIK JE BRLOKIRAN!");
					return rezultat;
				} else {
					session.setAttribute(KorisniciController.KORISNIK_KEY, korisnik);
					response.sendRedirect(bURL);
					System.out.println("USPESNA PRIJAVA");
					return null;
				}
			}
		}
		if (korisnickoIme.trim().equals("") && lozinka.trim().equals("")) {
			rezultat.addObject("poruka", "Morate uneti Korisnicko Ime i Lozinku!");
			System.out.println("Morate uneti podatke!");
			return rezultat;

		} else {
			rezultat.addObject("poruka", "Neispravno korisnicko ime ili lozinka!");
			System.out.println("Neispravni podaci!");
			return rezultat;
		}

	}

	@GetMapping(value = "/logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.invalidate();
		System.out.println("Korisnik je odjavljen!");
		response.sendRedirect(bURL);
	}

	@GetMapping(value = "/admin")
	public ModelAndView admin() {
		ModelAndView modelAndView = new ModelAndView("admin");
		return modelAndView;
	}

	/**
	 * Metoda za filtriranje korisnika na osnovu unetih kriterijuma.
	 * 
	 * @param korImeInput      - Korisničko ime koje se koristi za filtriranje
	 *                         korisnika.
	 * @param UlogaSelect      - Uloga korisnika koja se koristi za filtriranje
	 *                         (npr. PUTNIK, ADMIN).
	 * @param SortirajPoSelect - Polje po kojem se vrši sortiranje (npr. korisničko
	 *                         ime, uloga).
	 * @param SortOrderSelect  - Redosled sortiranja (rastuce ili opadajuce).
	 * @return ModelAndView - Model i prikaz sa filtriranim korisnicima.
	 */
	@PostMapping(value = "/filter")
	public ModelAndView filter(@RequestParam String korImeInput, @RequestParam String UlogaSelect,
			@RequestParam String SortirajPoSelect, @RequestParam String SortOrderSelect) {
		ModelAndView modelAndView = new ModelAndView("korisnici");
		List<Korisnik> korisnici = service.findAll();

		// Ispis unetog korisničkog imena za filtriranje
		System.out.println("Korisnicko ime:" + korImeInput);
		if (korImeInput != "") {
			// Dodavanje korisničkog imena u model i filtriranje liste korisnika
			System.out.println("KORISNICKOIME JE uneseno");
			modelAndView.addObject("korImeInput", korImeInput);
			korisnici.removeIf(k -> !k.getKorisnickoIme().contains(korImeInput)); // .startsWith(korImeInput)
		}

		// Filtriranje korisnika na osnovu uloge
		if (!UlogaSelect.equals("nista")) {
			System.out.println("ULOGA JE unesena");
			modelAndView.addObject("UlogaSelect", UlogaSelect);
			korisnici.removeIf(k -> !k.getUloga().name().equalsIgnoreCase(UlogaSelect));
		}

		// Sortiranje korisnika na osnovu izabranog polja i redosleda
		if (!SortirajPoSelect.equals("nista")) {
			Comparator<Korisnik> comparator = null;

			switch (SortirajPoSelect) {
			case "korisnickoIme":
				comparator = Comparator.comparing((Korisnik korisnik) -> korisnik.getKorisnickoIme(),
						String.CASE_INSENSITIVE_ORDER);
				break;
			case "uloga":
				comparator = Comparator.comparing((Korisnik korisnik) -> korisnik.getUloga().toString(),
						String.CASE_INSENSITIVE_ORDER);
				break;
			default:
				System.out.println("Nepoznata vrednost: " + SortirajPoSelect);
				break;
			}
			if (comparator != null) {
				if (SortOrderSelect.equalsIgnoreCase("opadajuce")) {
					comparator = comparator.reversed();
				}
				korisnici.sort(comparator);
			}

		}
		// Dodavanje poruke u model ako nema korisnika koji odgovaraju kriterijumima
		if (korisnici.isEmpty()) {
			modelAndView.addObject("nemakorisnikaporuka", "Nema korisnika koji odgovaraju zadatim kriterijumima!");
		}
		// Dodavanje filtriranih korisnika u model
		modelAndView.addObject("korisnici", korisnici);
		return modelAndView;
	}

}
