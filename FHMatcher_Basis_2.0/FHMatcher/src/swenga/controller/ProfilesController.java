package swenga.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import swenga.dao.ProfileDao;
import swenga.model.ProfilesModel;

@Controller
public class ProfilesController {
	
	@Autowired
	ProfileDao profileDao;
	
	@RequestMapping(value = { "/", "index" })
	public String index(Model model) {

		List<ProfilesModel> profiles = profileDao.getProfiles();

		model.addAttribute("profiles", profiles);
		return "index";
	}
	
	@RequestMapping("/fillData")
	@Transactional
	public String fillData(Model model) {

		Date now = new Date();

		ProfilesModel p1 = new ProfilesModel("Dominik", "Pagger", false, now);
		profileDao.persist(p1);

		ProfilesModel p2 = new ProfilesModel("Miriam", "Grainer", true, now);
		profileDao.persist(p2);

		ProfilesModel p3 = new ProfilesModel("Jane", "Doe", true, now);
		profileDao.persist(p3);

		return "forward:list";
	}
	
	@RequestMapping("/searchProfiles")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("profiles", profileDao.searchProfiles(searchString));

		return "index";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		profileDao.delete(id);

		return "forward:list";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profileCall() {
		return "profile";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String handleLogin() {
		return "registration";
	}
	

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

}
