package swenga.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import swenga.model.UserModel;
import swenga.model.UserRoleModel;
import swenga.dao.UserDao;
import swenga.dao.UserRoleDao;

@Controller
public class ProfilesController {
	
	@Autowired
	ProfileDao profileDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
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
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.GET)
	public String addProfile() {
		return "addProfile";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.POST)
	public String addProfile(@Valid ProfilesModel newProfilesModel, BindingResult bindingResult, Model model,
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("gender") String gender,
			@RequestParam("dayOfBirth") String dayOfBirth, @RequestParam("username") String username, @RequestParam("password") String password) {
		
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			
			return "forward:index";
		}
		List<UserModel> users = userDao.findByUsername(username);
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			calendar.setTime(formatDate.parse(dayOfBirth));
		} 
		catch (ParseException e) {				
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
		}
		
		Date birthday = calendar.getTime();
		
		if (users == null) {
			
			UserRoleModel userRole = userRoleDao.getRole("ROLE_USER");
			UserModel user = new UserModel(username, password, true);
			user.encryptPassword();
			user.addUserRole(userRole);
			userDao.persist(user);
			
			ProfilesModel profile = new ProfilesModel(firstname, lastname, Boolean.valueOf(gender), birthday);
		}
		else {
			model.addAttribute("errorMessage", "User already exists");
		}
		
		return "forward:index";
	}
	

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
	
	

}
