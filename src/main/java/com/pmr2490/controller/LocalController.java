package com.pmr2490.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.service.LocalService;

@Controller
@RequestMapping(value="/locals")
public class LocalController {

	private LocalService localService;
	
	@Autowired
	public LocalController(LocalService localService) {
		this.localService = localService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("local/index");
		mv.addObject("locals", this.localService.getAll());
		return mv;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String name) {
		this.localService.create(name);
		
		try {
			response.sendRedirect("/pmr2490/locals");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("local/show");
		mv.addObject("local", this.localService.get(id));
		return mv;
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		this.localService.delete(id);
		try {
			response.sendRedirect("/pmr2490/locals");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("local/edit");
		mv.addObject("local", this.localService.get(id));
		return mv;
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id,
			@RequestParam("name") String name) {
		
		this.localService.update(id, name);
		
		try {
			response.sendRedirect("/pmr2490/locals");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
