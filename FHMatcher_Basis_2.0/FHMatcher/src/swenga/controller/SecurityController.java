package swenga.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import swenga.dao.ProfileDao;
import swenga.dao.UserDao;
import swenga.dao.UserRoleDao;
import swenga.model.ProfilesModel;
import swenga.model.UserModel;
import swenga.model.UserRoleModel;

@Controller
public class SecurityController {
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	UserDao userDao;
	
	@RequestMapping("/fillUsers")
	@Transactional
	public String fillData(Model model) {
 
		UserRoleModel adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRoleModel("ROLE_ADMIN");
 
		UserRoleModel userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRoleModel("ROLE_USER");
 
		UserModel admin = new UserModel("admin", "password", true);
		admin.encryptPassword();
		admin.addUserRole(userRole);
		admin.addUserRole(adminRole);
		userDao.persist(admin);
 
		UserModel user = new UserModel("user", "password", true);
		user.encryptPassword();
		user.addUserRole(userRole);
		userDao.persist(user);
 
		return "forward:registration";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.GET)
	public String addProfile() {
		return "addProfile";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.POST)
	public String addProfile(@Valid ProfilesModel newProfilesModel, BindingResult bindingResult, Model model,
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("gender") boolean gender,
			@RequestParam("dayOfBirth") Date dayOfBirth, @RequestParam("username") String username, @RequestParam("password") String password) {
		
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			
			return "forward:/index";
		}
		List<UserModel> users = userDao.findByUsername(username);
		
		
		if (users == null) {
			
			UserRoleModel userRole = userRoleDao.getRole("ROLE_USER");
			UserModel user = new UserModel(username, password, true);
			user.encryptPassword();
			user.addUserRole(userRole);
			userDao.persist(user);
			
			ProfilesModel profile = new ProfilesModel(firstname, lastname, gender, dayOfBirth);
		}
		else {
			model.addAttribute("errorMessage", "User already exists");
		}
		
		return "forward:/index";
	}
 
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
 
		return "error";
 
	}

}
