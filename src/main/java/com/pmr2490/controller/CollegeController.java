package com.pmr2490.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public ModelAndView getAll(HttpServletRequest request) {
		
		try {
			ModelAndView modelAndView = new ModelAndView("college/index");
			modelAndView.addObject("colleges", this.collegeService.getAll());
			if(request.getParameter("deleted") != null)
				modelAndView.addObject("info_message", "<strong>Sucesso!</strong> Faculdade deletada com sucesso.");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
		
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public ModelAndView newForm() {
		return new ModelAndView("college/new");
	}
	
//	@RequestMapping(value="/new", method=RequestMethod.POST)
//	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam("name") String name) {
//		ModelAndView modelAndView = new ModelAndView();
//		List<String> errors = new ArrayList<String>();
//		try {
//			List<String> status = this.collegeService.create(name);
//			if (status.get(0).equals("success")) {
//				modelAndView.setViewName("college/show");
//				modelAndView.addObject("college", this.collegeService.get(Integer.parseInt(status.get(1))));
//				modelAndView.addObject("success_message", "Faculdade criada com sucesso");
//			}
//			else {
//				modelAndView.setViewName("college/new");
//				for (int i = 1; i < status.size(); i++) {
//					switch (status.get(i)) {
//					case "name.required":
//						errors.add("O campo nome é obrigatório.");
//						break;
//					case "name.toolong":
//						errors.add("O nome deve ter no máximo 50 caracteres.");
//						break;
//					case "name.existant":
//						errors.add("O nome " + name + " já existe.");
//						break;
//					default:
//						errors.add("Erro não mapeado.");
//						break;
//					}
//				}
//				modelAndView.addObject("errors", errors);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ModelAndView("error/unexpected-error");
//		}
//		return modelAndView;
//
//	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String insert(Model m, @RequestParam("name") String name) {
		
		try {
			List<String> status = this.collegeService.create(name);
			if (status.get(0).equals("success")) {
				return "redirect:/colleges/" + status.get(1) + "?success";
			}
			else {
				List<String> errors = new ArrayList<String>();
				for (int i = 1; i < status.size(); i++) {
					switch (status.get(i)) {
					case "name.required":
						errors.add("O campo nome é obrigatório.");
						break;
					case "name.toolong":
						errors.add("O nome deve ter no máximo 50 caracteres.");
						break;
					case "name.existant":
						errors.add("O nome " + name + " já existe.");
						break;
					default:
						errors.add("Erro não mapeado.");
						break;
					}
				}
				m.addAttribute("errors", errors);
				return "college/new";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}

	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(HttpServletRequest request, @PathVariable int id) {
		try {
			ModelAndView modelAndView = new ModelAndView("college/show");
			modelAndView.addObject("college", this.collegeService.get(id));
			if(request.getParameter("success") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Faculdade criada com sucesso.");
			if(request.getParameter("edited") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Faculdade editada com sucesso.");
			return modelAndView;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public String destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			this.collegeService.delete(id);
			return "redirect:/colleges?deleted";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.GET)
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
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response, Model m,
			@PathVariable int id, @RequestParam("name") String name) {
//		try {
//			this.collegeService.update(id, name);
//			return "redirect:/colleges/" + id + "?edited";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error/unexpected-error";
//		}
		
		try {
			List<String> status = this.collegeService.update(id, name);
			if (status.get(0).equals("success")) {
				return "redirect:/colleges/" + status.get(1) + "?edited";
			}
			else {
				List<String> errors = new ArrayList<String>();
				for (int i = 1; i < status.size(); i++) {
					switch (status.get(i)) {
					case "name.required":
						errors.add("O campo nome é obrigatório.");
						break;
					case "name.toolong":
						errors.add("O nome deve ter no máximo 50 caracteres.");
						break;
					case "name.existant":
						errors.add("O nome " + name + " já existe.");
						break;
					default:
						errors.add("Erro não mapeado.");
						break;
					}
				}
				m.addAttribute("errors", errors);
				m.addAttribute("college", this.collegeService.get(id));
				return "college/edit";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
	
}
