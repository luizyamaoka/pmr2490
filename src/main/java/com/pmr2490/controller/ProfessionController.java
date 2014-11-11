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

import com.pmr2490.service.ProfessionService;

@Controller
@RequestMapping(value="/professions")
public class ProfessionController {

	private ProfessionService professionService;
	
	@Autowired
	public ProfessionController(ProfessionService professionService) {
		this.professionService = professionService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("profession/index");
		mv.addObject("professions", this.professionService.getAll());
		return mv;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String name) {
		this.professionService.create(name);
		
		try {
			response.sendRedirect("/pmr2490/professions");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("profession/show");
		mv.addObject("profession", this.professionService.get(id));
		return mv;
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		this.professionService.delete(id);
		try {
			response.sendRedirect("/pmr2490/professions");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("profession/edit");
		mv.addObject("profession", this.professionService.get(id));
		return mv;
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id,
			@RequestParam("name") String name) {
		
		this.professionService.update(id, name);
		
		try {
			response.sendRedirect("/pmr2490/professions");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
