package com.pmr2490.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.model.College;
import com.pmr2490.model.Profession;
import com.pmr2490.service.UserService;

@Controller
@RequestMapping(value="/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
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
			@RequestParam("college") College college,
			@RequestParam("profession") Profession profession) {
	
		Calendar cal = Calendar.getInstance();
		cal.set(birthYear, birthMonth, birthDay);
		Date birthDate = cal.getTime();
		
		this.userService.create(firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, password, false, college, profession);
		
		try {
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
	public ModelAndView edit(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", this.userService.get(id));
		return new ModelAndView("user/edit", map);
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
			@RequestParam("password") String password,
			@RequestParam("password_confirmation") String passwordConfirmation,
			@RequestParam("college") College college,
			@RequestParam("profession") Profession profession) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(birthYear, birthMonth, birthDay);
		Date birthDate = cal.getTime();
		
		this.userService.update(id, firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, password, false, college, profession);
		
		try {
			response.sendRedirect("/pmr2490/users");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
