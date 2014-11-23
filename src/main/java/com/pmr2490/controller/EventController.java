package com.pmr2490.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.pmr2490.model.Local;
import com.pmr2490.model.Tag;
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
	public ModelAndView show(@PathVariable int id) {
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("event", this.eventService.get(id));
			return new ModelAndView("event/show", map);
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
			
			List<String> errors = new ArrayList<String>();
			
			if(eventDto.getName().isEmpty())
				errors.add("O nome do evento precisa ser preenchido.");
			if(eventDto.getEmail().isEmpty())
				errors.add("O email de contato precisa ser preenchido.");
			if(eventDto.getDayStart() == null || eventDto.getMonthStart() == null || eventDto.getYearStart() == null ||
					eventDto.getHourStart() == null || eventDto.getMinuteStart() == null)
				errors.add("A data de início do evneto precisa ser preenchida.");
			int phoneNumberLength = eventDto.getPhoneNumber().length();
			if (phoneNumberLength != 0 && (phoneNumberLength < 8 || phoneNumberLength > 9))
				errors.add("O numero de telefone deve ter 8 ou 9 caracteres");
			
			if (errors.isEmpty()) {
				try {
					
					if (phoneNumberLength == 0)
						eventDto.setPhoneNumber(null);
					
					Date dateStart = null;
					if (eventDto.getDayStart() != null && eventDto.getMonthStart() != null && eventDto.getYearStart() != null
							 && eventDto.getHourStart() != null && eventDto.getMinuteStart() != null) {
						Calendar cal = Calendar.getInstance();
						cal.set(eventDto.getYearStart(), eventDto.getMonthStart()-1, eventDto.getDayStart(),
								eventDto.getHourStart(), eventDto.getMinuteStart());
						dateStart = cal.getTime();
					}
					Date dateEnd = null;
					if (eventDto.getDayEnd() != null && eventDto.getMonthEnd() != null && eventDto.getYearEnd() != null
							 && eventDto.getHourEnd() != null && eventDto.getMinuteEnd() != null) {
						Calendar cal = Calendar.getInstance();
						cal.set(eventDto.getYearEnd(), eventDto.getMonthEnd()-1, eventDto.getDayEnd(),
								eventDto.getHourEnd(), eventDto.getMinuteEnd());
						dateEnd = cal.getTime();
					}
					
					Local local = eventDto.getLocalId() == null ? null : this.localService.get(eventDto.getLocalId());
					List<Tag> tags = new ArrayList<Tag>();
					for (Integer tagId : eventDto.getTagIds())
						tags.add(this.tagService.get(tagId));
					
					int eventId = this.eventService.create(eventDto.getName(), dateStart, dateEnd, eventDto.getEmail(), 
							eventDto.getPhoneDdd(), eventDto.getPhoneNumber(), eventDto.getDescription(), 
							creator, local, tags);
					eventDto.setId(eventId);
				}
				catch(Exception e) {
					errors.add("Um erro inesperado ocorreu ao efetuar o cadastro. Por favor tente novamente ou contacte o responsavel.");
				}
			}
			
	        if(!errors.isEmpty()) {
	        	m.addAttribute("errors", errors);
	        	m.addAttribute("tags", this.tagService.getAll());
	     		m.addAttribute("locals", this.localService.getAll());
	            return "event/new";
	        }
	         
	        m.addAttribute("success_message", "Evento criado com sucesso");
	        m.addAttribute("event", this.eventService.get(eventDto.getId()));
	        return "event/show";
		}
        catch (Exception ex) {
        	ex.printStackTrace();
        	return "error/unexpected-error";
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
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
    public String update(@PathVariable int id, @Valid EventDto eventDto, BindingResult result, Model m) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User creator = this.userService.getByEmail(email);
			
			if (creator == null || this.eventService.get(id).getCreator().getId() != creator.getId())
				return "error/403";
			
			List<String> errors = new ArrayList<String>();
			
			if(eventDto.getName().isEmpty())
				errors.add("O nome do evento precisa ser preenchido.");
			if(eventDto.getEmail().isEmpty())
				errors.add("O email de contato precisa ser preenchido.");
			if(eventDto.getDayStart() == null || eventDto.getMonthStart() == null || eventDto.getYearStart() == null ||
					eventDto.getHourStart() == null || eventDto.getMinuteStart() == null)
				errors.add("A data de início do evneto precisa ser preenchida.");
			int phoneNumberLength = eventDto.getPhoneNumber().length();
			if (phoneNumberLength != 0 && (phoneNumberLength < 8 || phoneNumberLength > 9))
				errors.add("O numero de telefone deve ter 8 ou 9 caracteres");
			
			if (errors.isEmpty()) {
				try {
					
					if (phoneNumberLength == 0)
						eventDto.setPhoneNumber(null);
					
					Date dateStart = null;
					if (eventDto.getDayStart() != null && eventDto.getMonthStart() != null && eventDto.getYearStart() != null
							 && eventDto.getHourStart() != null && eventDto.getMinuteStart() != null) {
						Calendar cal = Calendar.getInstance();
						cal.set(eventDto.getYearStart(), eventDto.getMonthStart()-1, eventDto.getDayStart(),
								eventDto.getHourStart(), eventDto.getMinuteStart());
						dateStart = cal.getTime();
					}
					Date dateEnd = null;
					if (eventDto.getDayEnd() != null && eventDto.getMonthEnd() != null && eventDto.getYearEnd() != null
							 && eventDto.getHourEnd() != null && eventDto.getMinuteEnd() != null) {
						Calendar cal = Calendar.getInstance();
						cal.set(eventDto.getYearEnd(), eventDto.getMonthEnd()-1, eventDto.getDayEnd(),
								eventDto.getHourEnd(), eventDto.getMinuteEnd());
						dateEnd = cal.getTime();
					}
					
					Local local = eventDto.getLocalId() == null ? null : this.localService.get(eventDto.getLocalId());
					List<Tag> tags = new ArrayList<Tag>();
					for (Integer tagId : eventDto.getTagIds())
						tags.add(this.tagService.get(tagId));
					
					this.eventService.update(id, eventDto.getName(), dateStart, dateEnd, eventDto.getEmail(), 
							eventDto.getPhoneDdd(), eventDto.getPhoneNumber(), eventDto.getDescription(), 
							local, tags);
				}
				catch(Exception e) {
					e.printStackTrace();
					errors.add("Um erro inesperado ocorreu ao efetuar o cadastro. Por favor tente novamente ou contacte o responsavel.");
				}
			}
			
	        if(!errors.isEmpty()) {
	        	m.addAttribute("errors", errors);
	        	m.addAttribute("tags", this.tagService.getAll());
	     		m.addAttribute("locals", this.localService.getAll());
	            return "event/edit";
	        }
	         
	        m.addAttribute("success_message", "Evento criado com sucesso");
	        m.addAttribute("event", this.eventService.get(eventDto.getId()));
	        return "event/show";
		}
		catch (Exception ex) {
			ex.printStackTrace();
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
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			this.eventService.delete(id);
			response.sendRedirect("/pmr2490/events");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
	
}
