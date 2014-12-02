package com.pmr2490.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.service.EventService;

@Controller
public class StaticPagesController {
	
	private EventService eventService;
	
	@Autowired
	public StaticPagesController(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(value="/")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		try {
			ModelAndView modelAndView = new ModelAndView("static-pages/home");
			
			if(request.getParameter("logout") != null)
				modelAndView.addObject("info_message", "<strong>Tchau!</strong> Logout efetuado com sucesso.");
			
			modelAndView.addObject("events", this.eventService.getAll(12));
			if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				modelAndView.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
			}
			
			return modelAndView;
		} catch (Exception e) {
			return new ModelAndView("error/unexpected-error");
		}
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
