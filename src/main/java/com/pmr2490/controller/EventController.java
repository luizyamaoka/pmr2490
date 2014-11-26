package com.pmr2490.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pmr2490.dto.EventDto;
import com.pmr2490.model.Event;
import com.pmr2490.model.User;
import com.pmr2490.service.EventService;
import com.pmr2490.service.LocalService;
import com.pmr2490.service.TagService;
import com.pmr2490.service.UserService;

@Controller
@RequestMapping(value="/events")
public class EventController {

	private EventService eventService;
	private LocalService localService;
	private UserService userService;
	private TagService tagService;
	
	private static final Map<String, String> ERROR_MESSAGES;
	static {
		ERROR_MESSAGES = new HashMap<String, String>();
		ERROR_MESSAGES.put("name.required", "O nome do evento precisa ser preenchido.");
		ERROR_MESSAGES.put("email.required", "O email precisa ser preenchido.");
		ERROR_MESSAGES.put("creatorId.required", "É preciso estar logado para criar um evento.");
		ERROR_MESSAGES.put("localId.required", "O local do evento precisa ser preenchido.");
		ERROR_MESSAGES.put("birthYear.past", "O ano de nascimento não pode ser no futuro.");
		ERROR_MESSAGES.put("monthStart.impossible", "O mês de início é inválido.");
		ERROR_MESSAGES.put("dayStart.impossible", "O dia de início é inválido.");
		ERROR_MESSAGES.put("hourStart.impossible", "A hora de início é inválida.");
		ERROR_MESSAGES.put("minuteStart.impossible", "O minuto de início é inválido.");
		ERROR_MESSAGES.put("dateStart.required", "A data e horário de início precisam ser preenchidos.");
		ERROR_MESSAGES.put("monthEnd.impossible", "O mês de término é inválido.");
		ERROR_MESSAGES.put("dayEnd.impossible", "O dia de término é inválido.");
		ERROR_MESSAGES.put("hourEnd.impossible", "A hora de término é inválida.");
		ERROR_MESSAGES.put("minuteEnd.impossible", "O minute de término é inválido.");
		ERROR_MESSAGES.put("dateEnd.incomplete", "A data de término do evento está incompleta.");
		ERROR_MESSAGES.put("phoneNumber.length", "O numero de telefone deve ter 8 ou 9 caracteres.");
		ERROR_MESSAGES.put("phoneNumber.incomplete", "O numero de telefone está incompleto.");
		ERROR_MESSAGES.put("description.length", "A descrição deve ter no máximo 255 caracteres.");
		
	}
	
	@Autowired
	public EventController(EventService eventService, LocalService localService, 
			UserService userService, TagService tagService) {
		this.eventService = eventService;
		this.localService = localService;
		this.userService = userService;
		this.tagService = tagService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index() {
		try {
			ModelAndView modelAndView = new ModelAndView("event/index");
			modelAndView.addObject("events", this.eventService.getAll());
			if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				modelAndView.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
			}
			return modelAndView;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(HttpServletRequest request, @PathVariable int id) {
		try{
			ModelAndView modelAndView = new ModelAndView("event/show");
			modelAndView.addObject("event", this.eventService.getEager(id));
			modelAndView.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
			if(request.getParameter("success") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Evento criado com sucesso.");
			if(request.getParameter("edited") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Evento editado com sucesso.");
			return modelAndView;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
    public ModelAndView newForm() {
		try {
			ModelAndView modelAndView = new ModelAndView("event/new");
			modelAndView.addObject("eventDto", new EventDto());
			modelAndView.addObject("tags", this.tagService.getAll());
			modelAndView.addObject("locals", this.localService.getAll());
	        return modelAndView;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
    }
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
    public String insert(@Valid EventDto eventDto, BindingResult result, Model m) {
		
		try { 
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User creator = this.userService.getByEmail(email);
			
			if (creator == null || eventDto.getCreatorId() != creator.getId())
				return "error/403";
			
			eventDto.setCreatorId(creator.getId());
			
			List<String> status = this.eventService.create(eventDto);
			
			if(status.get(0).equals("error")) {
	        	List<String> errors = new ArrayList<String>();
	        	for (int i = 1; i < status.size(); i++)
	        		errors.add(ERROR_MESSAGES.get(status.get(i)));
	        	m.addAttribute("errors", errors);
	        	m.addAttribute("tags", this.tagService.getAll());
	     		m.addAttribute("locals", this.localService.getAll());
	            return "event/new";
	        }
	         
	        return "redirect:/events/" + status.get(1) + "?success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
		
    }
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
    public ModelAndView editForm(@PathVariable int id) {
		
		try {
			EventDto eventDto = this.eventService.getEventDto(id);
			
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User creator = this.userService.getByEmail(email);
			
			if (creator == null || eventDto.getCreatorId() != creator.getId())
				return new ModelAndView("error/403");
			
			ModelAndView modelAndView = new ModelAndView("event/edit");
			modelAndView.addObject("eventDto", eventDto);
			modelAndView.addObject("tags", this.tagService.getAll());
			modelAndView.addObject("locals", this.localService.getAll());
	        return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
    }
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
    public String update(@PathVariable int id, @Valid EventDto eventDto, BindingResult result, 
    		Model m, HttpServletResponse response) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User creator = this.userService.getByEmail(email);
			
			if (creator == null || this.eventService.get(id).getCreator().getId() != creator.getId())
				return "error/403";
			
			eventDto.setCreatorId(creator.getId());
			
			List<String> status = this.eventService.update(eventDto);
			
	        if(status.get(0).equals("error")) {
	        	List<String> errors = new ArrayList<String>();
	        	for (int i = 1; i < status.size(); i++)
	        		errors.add(ERROR_MESSAGES.get(status.get(i)));
	        	m.addAttribute("errors", errors);
	        	m.addAttribute("tags", this.tagService.getAll());
	     		m.addAttribute("locals", this.localService.getAll());
	            return "event/edit";
	        }
	         
	        return "redirect:/events/" + status.get(1) + "?edited";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
    }
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public String destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			this.eventService.delete(id);
			return "redirect:/events?destroyed";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			ModelAndView modelAndView = new ModelAndView("event/search");
			modelAndView.addObject("locals", this.localService.getAll());
			modelAndView.addObject("tags", this.tagService.getAll());
			return modelAndView;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
		
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView doSearch(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="date", required=false) String date,
			@RequestParam(value="localId", required=false) Integer localId,
			@RequestParam(value="tagId", required=false) Integer tagId) {
		try {
			ModelAndView modelAndView = new ModelAndView("event/index");
			String data = date.equals("") ? null : date;
			modelAndView.addObject("events", this.eventService.getBySet(data, name, localId, tagId));
			if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				modelAndView.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
			}
			return modelAndView;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="{id}/edit-tags", method=RequestMethod.GET)
    public ModelAndView editTagsForm(@PathVariable int id) {
		
		try {
			Event event = this.eventService.getEager(id);
			
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User creator = this.userService.getByEmail(email);
			
			if (creator == null || event.getCreator().getId() != creator.getId())
				return new ModelAndView("error/403");
			
			ModelAndView modelAndView = new ModelAndView("event/tags");
			modelAndView.addObject("tags", this.tagService.getAll());
			modelAndView.addObject("event", event);
	        return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
    }
	
}
