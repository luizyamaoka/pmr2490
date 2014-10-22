package com.pmr2490.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		map.put("users", userService.index());
		return new ModelAndView("user/index", map);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response) {
		
		String name = request.getParameter("name");
		
		this.userService.create(name);
		
		try {
			response.sendRedirect("/pmr2490/users");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", userService.findById(id));
		return new ModelAndView("user/show", map);
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		this.userService.destroy(id);
		try {
			response.sendRedirect("/pmr2490/users");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", userService.findById(id));
		return new ModelAndView("user/edit", map);
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		
		String name = request.getParameter("name");
		
		this.userService.edit(id, name);
		
		try {
			response.sendRedirect("/pmr2490/users");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
