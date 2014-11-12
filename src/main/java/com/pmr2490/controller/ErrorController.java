package com.pmr2490.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	
	@RequestMapping("/403")
	public ModelAndView deniedAccess() {
		return new ModelAndView("error/403");
	}
	
}
