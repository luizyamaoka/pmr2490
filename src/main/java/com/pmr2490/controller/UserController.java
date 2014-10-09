package com.pmr2490.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/index")
	public ModelAndView index() {
		Map<String, Object> map = new HashMap<>();
		map.put("users", userService.index());
		return new ModelAndView("user/index", map);
	}
	
}
