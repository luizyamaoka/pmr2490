package com.pmr2490.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.model.College;
import com.pmr2490.model.Profession;
import com.pmr2490.model.User;
import com.pmr2490.service.CollegeService;
import com.pmr2490.service.ProfessionService;
import com.pmr2490.service.UserService;

@Controller
@RequestMapping(value="/users")
public class UserController {

	private UserService userService;
	private ProfessionService professionService;
	private CollegeService collegeService;
	
	@Autowired
	public UserController(UserService userService, ProfessionService professionService, 
			CollegeService collegeService) {
		this.userService = userService;
		this.professionService = professionService;
		this.collegeService = collegeService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index() {
		Map<String, Object> map = new HashMap<>();
		map.put("users", userService.getAll());
		return new ModelAndView("user/index", map);
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("user/new");
		modelAndView.addObject("colleges", this.collegeService.getAll());
		modelAndView.addObject("professions", this.professionService.getAll());
		
		if(request.getParameter("passwords_not_matching") != null)
			modelAndView.addObject("error_message", "<strong>Erro!</strong> A senha e sua confirmação devem ser iguais.");
		if(request.getParameter("existant_email") != null)
			modelAndView.addObject("error_message", "<strong>Erro!</strong> Este e-mail já está cadastrado.");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="first_name") String firstName,
			@RequestParam(value="last_name") String lastName,
			@RequestParam(value="birth_day", required=false) Integer birthDay,
			@RequestParam(value="birth_month", required=false) Integer birthMonth,
			@RequestParam(value="birth_year", required=false) Integer birthYear,
			@RequestParam(value="genre", required=false) String genre,
			@RequestParam(value="phone_ddd", required=false) Integer phoneDdd,
			@RequestParam(value="phone_number", required=false) String phoneNumber,
			@RequestParam(value="email") String email,
			@RequestParam(value="password") String password,
			@RequestParam(value="password_confirmation") String passwordConfirmation,
			@RequestParam(value="college_id", required=false) Integer collegeId,
			@RequestParam(value="profession_id") Integer professionId) {	
		
		try {
		
			if (!password.equals(passwordConfirmation))
				response.sendRedirect("/pmr2490/users/new?passwords_not_matching");
			else if (professionId == 0) {
				response.sendRedirect("/pmr2490/users/new?profession_missing");
			}
			else if (this.userService.getByEmail(email) != null)
				response.sendRedirect("/pmr2490/users/new?existant_email");
			else {
		
				Date birthDate = null;
				if (birthDay != null && birthMonth != null && birthYear != null) {
					Calendar cal = Calendar.getInstance();
					cal.set(birthYear, birthMonth-1, birthDay);
					birthDate = cal.getTime();
				}
				
				phoneNumber = phoneNumber.equals("") ? null : phoneNumber;
				genre = genre.equals("0") ? null : genre;
				
				College college = collegeId == 0 ? null : this.collegeService.get(collegeId);
				Profession profession = this.professionService.get(professionId);
				
				this.userService.create(firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, password, false, college, profession);
			
				response.sendRedirect("/pmr2490/login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("user/show");
		modelAndView.addObject("user", this.userService.get(id));
		return modelAndView;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile() {
		ModelAndView modelAndView = new ModelAndView("user/show");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		modelAndView.addObject("user", this.userService.getByEmail(email));
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
		
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {
				this.userService.delete(id);
				response.sendRedirect("/pmr2490/users");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int id) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {
				ModelAndView modelAndView = new ModelAndView("user/edit");
				modelAndView.addObject("user", this.userService.get(id));
				modelAndView.addObject("colleges", this.collegeService.getAll());
				modelAndView.addObject("professions", this.professionService.getAll());
				
				if(request.getParameter("passwords_not_matching") != null)
					modelAndView.addObject("error_message", "<strong>Erro!</strong> A senha e sua confirmação devem ser iguais.");
				return modelAndView;
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("error/403");
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id,
			@RequestParam(value="first_name") String firstName,
			@RequestParam(value="last_name") String lastName,
			@RequestParam(value="birth_day", required=false) Integer birthDay,
			@RequestParam(value="birth_month", required=false) Integer birthMonth,
			@RequestParam(value="birth_year", required=false) Integer birthYear,
			@RequestParam(value="genre", required=false) String genre,
			@RequestParam(value="phone_ddd", required=false) Integer phoneDdd,
			@RequestParam(value="phone_number", required=false) String phoneNumber,
			@RequestParam(value="email") String email,
			@RequestParam(value="college_id", required=false) Integer collegeId,
			@RequestParam(value="profession_id") Integer professionId) {
		
		try {
			String accessEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(accessEmail);
		
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {

				Date birthDate = null;
				if (birthDay != null && birthMonth != null && birthYear != null) {
					Calendar cal = Calendar.getInstance();
					cal.set(birthYear, birthMonth-1, birthDay);
					birthDate = cal.getTime();
				}
				
				phoneNumber = phoneNumber.equals("") ? null : phoneNumber;
				genre = genre.equals("0") ? null : genre;
				
				College college = collegeId == null ? null : this.collegeService.get(collegeId);
				Profession profession = this.professionService.get(professionId);
				
				this.userService.update(id, firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, false, college, profession, null);
			
				response.sendRedirect("/pmr2490/users");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/edit-password")
	public ModelAndView editPassword(HttpServletRequest request, HttpServletResponse response) {
	
		ModelAndView modelAndView = new ModelAndView("user/edit-password");
		if(request.getParameter("wrong_password") != null)
			modelAndView.addObject("error_message", "<strong>Senha incorreta!</strong> Tente novamente.");
		else if(request.getParameter("passwords_not_matching") != null)
			modelAndView.addObject("error_message", "<strong>Erro!</strong> A nova senha e sua confirmação devem ser iguais.");
		else if(request.getParameter("success") != null)
			modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Senha alterada.");
		return modelAndView;

	}
	
	@RequestMapping(value="/update-password")
	public void updatePassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("password") String password,
			@RequestParam("new_password") String newPassword,
			@RequestParam("new_password_confirmation") String newPasswordConfirmation) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			if (!user.isPasswordCorrect(password))
				response.sendRedirect("/pmr2490/users/edit-password?wrong_password");
			else if (!newPassword.equals(newPasswordConfirmation))
				response.sendRedirect("/pmr2490/users/edit-password?passwords_not_matching");
			else {
				this.userService.update(user.getId(), null, null, null, null, null, null, null, null, null, null, newPassword);
				response.sendRedirect("/pmr2490/users/edit-password?success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
