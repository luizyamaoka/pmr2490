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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.service.CollegeService;

@Controller
@RequestMapping("/colleges")
public class CollegeController {

	private CollegeService collegeService;
	
	@Autowired
	public CollegeController(CollegeService collegeService) {
		this.collegeService = collegeService;
	}
	
	@RequestMapping("")
	public ModelAndView getAll() {
		
		try {
			ModelAndView modelAndView = new ModelAndView("college/index");
			modelAndView.addObject("colleges", this.collegeService.getAll());
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
		
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("name") String name) {
		
		try {
			this.collegeService.create(name);
			response.sendRedirect("/pmr2490/colleges");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("college", this.collegeService.get(id));
			return new ModelAndView("college/show", map);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			this.collegeService.delete(id);
			response.sendRedirect("/pmr2490/colleges");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(@PathVariable int id) {
		try {
			ModelAndView modelAndView = new ModelAndView("college/edit");
			modelAndView.addObject("college", this.collegeService.get(id));
			return modelAndView;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id, @RequestParam("name") String name) {
		try {
			this.collegeService.update(id, name);
			response.sendRedirect("/pmr2490/colleges");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
