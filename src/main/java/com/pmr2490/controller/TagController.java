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

import com.pmr2490.service.TagService;

@Controller
@RequestMapping(value="/tags")
public class TagController {

	private TagService tagService;
	
	@Autowired
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		try {
			ModelAndView modelAndView = new ModelAndView("tag/index");
			modelAndView.addObject("tags", this.tagService.getAll());
			if(request.getParameter("deleted") != null)
				modelAndView.addObject("info_message", "<strong>Sucesso!</strong> Tag deletada com sucesso.");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public ModelAndView newForm() {
		return new ModelAndView("tag/new");
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String name) {
		try {
			this.tagService.create(name);
			response.sendRedirect("/pmr2490/tags");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(HttpServletRequest request, @PathVariable int id) {
		try {
			ModelAndView modelAndView = new ModelAndView("tag/show");
			modelAndView.addObject("tag", this.tagService.get(id));
			if(request.getParameter("success") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Tag criada com sucesso.");
			if(request.getParameter("edited") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Tag editada com sucesso.");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public String destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			this.tagService.delete(id);
			return "redirect:/tags?deleted";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(@PathVariable int id) {
		try {
			ModelAndView modelAndView = new ModelAndView("tag/edit");
			modelAndView.addObject("tag", this.tagService.get(id));
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id,
			@RequestParam("name") String name) {
		try {
			this.tagService.update(id, name);
			response.sendRedirect("/pmr2490/tags");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
