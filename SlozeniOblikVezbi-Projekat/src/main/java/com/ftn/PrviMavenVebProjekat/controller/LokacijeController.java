package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.model.Lokacije;

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
	private ApplicationMemory memorijaAplikacije;

	/** pristup ApplicationContext */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/** inicijalizacija podataka za kontroler */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		memorijaAplikacije = applicationContext.getBean(ApplicationMemory.class);
		Lokacije lokacije = new Lokacije();

//		servletContext.setAttribute(lokacijeController.lokacije_KEY, lokacije);	

		memorijaAplikacije.put(LokacijeController.LOKACIJE_KEY, lokacije);
	}

	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev */
	// GET: lokacije
	@GetMapping
	@ResponseBody
	public String index() {
		Lokacije lokacije = (Lokacije) memorijaAplikacije.get(LOKACIJE_KEY);
		List<Lokacija> lokacijeList = lokacije.findAll();
		String retHTML = "";
		retHTML += "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<meta charset=\"UTF-8\"> \r\n"
				+ "<title>Knjige</title>\r\n"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"/PrviMavenVebProjekat/css/StiloviTabela.css\"/>\r\n"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"/PrviMavenVebProjekat/css/StiloviHorizontalniMeni.css\"/>		\r\n"
				+ "</head>\r\n" + "<body>";

		for (int i = 0; i < lokacijeList.size(); i++) {
			int ivece = i + 1;
			Lokacija lokacija = lokacijeList.get(i);
			retHTML += "<h1>Lokacija broj: " + ivece + "</h1>" + "<p>Grad: " + lokacija.getGrad() + "</p>"
					+ "<p>Drzava: " + lokacija.getDrzava() + "</p>" + "<p>Kontinent: " + lokacija.getKontinent()
					+ "</p>" + "<form action=\"/PrviMavenVebProjekat/lokacije/delete?id=" + lokacija.getId() + "\" method=\"post\">"
//					+ "<input type=\"hidden\" name=\"id\" value=\"" + i + "\" />"
					+ "<input type=\"submit\" value=\"Obrisi\" />" + "</form>";
		}

		retHTML += "<a href=\"index.html\">Pocetna</a>\r\n" + "</body>\r\n" + "</html>";
		return retHTML;
	}

	/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev */
	// GET: lokacije/dodaj
	@GetMapping(value = "/add")
	public String create() {
		return "/dodaj-lokaciju.html";
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: lokacije/add
	@PostMapping(value = "/add")
	public void create(@RequestParam String grad, @RequestParam String drzava, @RequestParam String kontinent,
			HttpServletResponse response) throws IOException {
		Lokacije lokacije = (Lokacije) memorijaAplikacije.get(LOKACIJE_KEY);
		if (grad.equals("") || drzava.equals("")) {
			response.sendRedirect(bURL + "greska.html");
			return;
		}
		lokacije.save(new Lokacija(grad, drzava, kontinent));
		response.sendRedirect(bURL + "lokacije");
		return;

	}

	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	// POST: lokacije/edit
	@PostMapping(value = "/edit")
	public void edit(@ModelAttribute Lokacija LokacijaEdited, HttpServletResponse response) throws IOException {

	}

	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: lokacije/delete
	@PostMapping(value = "/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		Lokacije lokacije = (Lokacije) memorijaAplikacije.get(LOKACIJE_KEY);
		lokacije.delete(id);
		response.sendRedirect(bURL + "lokacije");
	}

	/** pribavnjanje HTML stanice za prikaz određenog entiteta , get zahtev */
	// GET: lokacije/details?id=1
	@GetMapping(value = "/details")
	@ResponseBody
	public void details(@RequestParam Long id) {
		return;
	}
}
