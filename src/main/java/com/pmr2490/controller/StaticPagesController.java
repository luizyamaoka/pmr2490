package com.pmr2490.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticPagesController {

	@RequestMapping(value="/")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("static-pages/home");
		
		if(request.getParameter("logout") != null)
			modelAndView.addObject("info_message", "<strong>Tchau!</strong> Logout efetuado com sucesso.");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("static-pages/login");
		
		if(request.getParameter("error") != null)
			modelAndView.addObject("error_message", "<strong>Erro!</strong> Usuário e/ou senha incorretos.");
		if(request.getParameter("user") != null)
			modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Usuário criado com sucesso.");
		
		return modelAndView;
	}
	
}
