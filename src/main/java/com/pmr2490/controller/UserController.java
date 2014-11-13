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
		return modelAndView;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("first_name") String firstName,
			@RequestParam("last_name") String lastName,
			@RequestParam("birth_day") Integer birthDay,
			@RequestParam("birth_month") Integer birthMonth,
			@RequestParam("birth_year") Integer birthYear,
			@RequestParam("genre") String genre,
			@RequestParam("phone_ddd") Integer phoneDdd,
			@RequestParam("phone_number") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("password_confirmation") String passwordConfirmation,
			@RequestParam("college_id") Integer collegeId,
			@RequestParam("profession_id") Integer professionId) {
		
		try {
		
			if (!password.equals(passwordConfirmation))
				response.sendRedirect("/pmr2490/user/new?passwords_not_matching");
		
			Calendar cal = Calendar.getInstance();
			cal.set(birthYear, birthMonth-1, birthDay);
			Date birthDate = cal.getTime();
			
			College college = this.collegeService.get(collegeId);
			Profession profession = this.professionService.get(professionId);
			
			this.userService.create(firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, password, false, college, profession);
		
			response.sendRedirect("/pmr2490/users");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", this.userService.get(id));
		return new ModelAndView("user/show", map);
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		this.userService.delete(id);
		try {
			response.sendRedirect("/pmr2490/users");
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
			@RequestParam("first_name") String firstName,
			@RequestParam("last_name") String lastName,
			@RequestParam("birth_day") Integer birthDay,
			@RequestParam("birth_month") Integer birthMonth,
			@RequestParam("birth_year") Integer birthYear,
			@RequestParam("genre") String genre,
			@RequestParam("phone_ddd") Integer phoneDdd,
			@RequestParam("phone_number") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("college_id") Integer collegeId,
			@RequestParam("profession_id") Integer professionId) {
		
		try {
			String accessEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(accessEmail);
		
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {

				Calendar cal = Calendar.getInstance();
				cal.set(birthYear, birthMonth, birthDay);
				Date birthDate = cal.getTime();
				
				College college = this.collegeService.get(collegeId);
				Profession profession = this.professionService.get(professionId);
				
				this.userService.update(id, firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, false, college, profession, null);
			
				response.sendRedirect("/pmr2490/users");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit-password")
	public ModelAndView editPassword(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int id) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
		
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {
				ModelAndView modelAndView = new ModelAndView("user/edit-password");
				if(request.getParameter("wrong_password") != null)
					modelAndView.addObject("error_message", "<strong>Senha incorreta!</strong> Tente novamente.");
				else if(request.getParameter("passwords_not_matching") != null)
					modelAndView.addObject("error_message", "<strong>Erro!</strong> A nova senha e sua confirmação devem ser iguais.");
				else if(request.getParameter("success") != null)
					modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Senha alterada.");
				return modelAndView;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("error/403");
	}
	
	@RequestMapping(value="/{id}/update-password")
	public void updatePassword(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int id, 
			@RequestParam("password") String password,
			@RequestParam("new_password") String newPassword,
			@RequestParam("new_password_confirmation") String newPasswordConfirmation) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else if (!user.isPasswordCorrect(password))
				response.sendRedirect("/pmr2490/users/"+id+"/edit-password?wrong_password");
			else if (!newPassword.equals(newPasswordConfirmation))
				response.sendRedirect("/pmr2490/users/"+id+"/edit-password?passwords_not_matching");
			else {
				this.userService.update(id, null, null, null, null, null, null, null, null, null, null, newPassword);
				response.sendRedirect("/pmr2490/users/"+id+"/edit-password?success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
