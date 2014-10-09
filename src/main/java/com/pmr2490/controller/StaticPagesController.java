package com.pmr2490.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticPagesController {

	@RequestMapping(value="/")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("index");
	}
	
}
