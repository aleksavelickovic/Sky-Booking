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
	private LokacijaService service;

	/** pristup ApplicationContext */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
		applicationContext.getBean(ApplicationMemory.class);
	}

	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev */
	// GET: lokacije
	@GetMapping
	@ResponseBody
	public String index() {
		List<Lokacija> lokacijeList = service.findAll();
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
					+ "<a href=\"lokacije/edit?id="+lokacija.getId()+"\">Izmeni lokaciju</a>"
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
	public String edit(@RequestParam Long id) {
		String retHTML = "";
		Lokacija lokacijaZaEdit = service.findOne(id);
		
		retHTML += "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "<meta charset=\"UTF-8\">\r\n"
				+ "<title>Izmeni lokaciju</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "\r\n"
				+ "<form action=\"/PrviMavenVebProjekat/lokacije/edit\" method=\"post\">\r\n"
				+ "		<label for=\"grad\" >Grad: </label>\r\n"
				+ "		<input type = \"text\" name= \"grad\" value=\""+lokacijaZaEdit.getGrad()+"\"/> <br>\r\n"
				+ "		<label for=\"Drzava\">Drzava: </label>\r\n"
				+ "		<input type = \"text\" name= \"drzava\" value=\""+lokacijaZaEdit.getDrzava()+"\"/> <br>\r\n"
				+ "		<select name=\"kontinent\">\r\n"
				+ "			<option value=\"Evropa\" "+("Evropa".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Evropa</option>\r\n"
				+ "  		<option value=\"Amerika\""+("Amerika".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Amerika</option>\r\n"
				+ "  		<option value=\"Azija\""+("Azija".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Azija</option>\r\n"
				+ "  		<option value=\"Australija\""+("Australija".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Australija</option>\r\n"
				+ "  		<option value=\"Afrika\""+("Afrika".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Afrika</option>\r\n"
				+ "  		<option value=\"Antartika\""+("Antartika".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Antartikak</option>\r\n"
				+ "  		<option value=\"Okeanija\""+("Okeanija".equals(lokacijaZaEdit.getKontinent().toString()) ? "selected" : "")+">Okeanija</option>\r\n"
				+ "		</select>\r\n"
				+ "		 <input type=\"submit\" value=\"Potvrdi\">\r\n"
				+ "	</form>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
		return retHTML;
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
		service.delete(id);
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
