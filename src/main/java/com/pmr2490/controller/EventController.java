package com.pmr2490.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
		ModelAndView modelAndView = new ModelAndView("event/index");
		modelAndView.addObject("events", this.eventService.getAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("event/new");
		modelAndView.addObject("locals", this.localService.getAll());
		modelAndView.addObject("tags", this.tagService.getAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="name") String name,
			@RequestParam(value="date_start") String dateStartString,
			@RequestParam(value="hour_start") Integer hourStart,
			@RequestParam(value="minute_start") Integer minuteStart,
			@RequestParam(value="date_end", required=false) String dateEndString,
			@RequestParam(value="hour_end", required=false) Integer hourEnd,
			@RequestParam(value="minute_end", required=false) Integer minuteEnd,
			@RequestParam(value="email") String email,
			@RequestParam(value="phone_ddd", required=false) Integer phoneDdd,
			@RequestParam(value="phone_number", required=false) String phoneNumber,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="local_id") Integer localId,
			@RequestParam(value="tag_ids[]", required=false) Integer[] tagIds) {
		
		try {
		
			if (localId == 0)
				response.sendRedirect("/pmr2490/events/new?local_missing");
			else {
				DateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");
				
				Date startDate = df.parse(dateStartString + " " + hourStart + ":" + minuteStart);
				
				Date endDate = null;
				if (!dateEndString.equals("") && hourEnd != null && minuteEnd != null)
					endDate = df.parse(dateEndString + " " + hourEnd + ":" + minuteEnd);
				
				phoneNumber = phoneNumber.equals("") ? null : phoneNumber;
				
				Local local = this.localService.get(localId);
				
				String accessEmail = SecurityContextHolder.getContext().getAuthentication().getName();
				User creator = this.userService.getByEmail(accessEmail);
				
				List<Tag> tags = new ArrayList<Tag>();
				if (tagIds != null)
					for(int tagId : tagIds)
						tags.add(this.tagService.get(tagId));
				
				this.eventService.create(name, startDate, endDate, email, phoneDdd, phoneNumber, 
						description, creator, local, tags);
				
				response.sendRedirect("/pmr2490/events");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("event", this.eventService.get(id));
		return new ModelAndView("event/show", map);
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		this.eventService.delete(id);
		try {
			response.sendRedirect("/pmr2490/events");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("event/edit");
		modelAndView.addObject("event", this.eventService.get(id));
		modelAndView.addObject("locals", this.localService.getAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id,
			@RequestParam(value="name") String name,
			@RequestParam(value="date_start") String dateStartString,
			@RequestParam(value="hour_start") Integer hourStart,
			@RequestParam(value="minute_start") Integer minuteStart,
			@RequestParam(value="date_end", required=false) String dateEndString,
			@RequestParam(value="hour_end", required=false) Integer hourEnd,
			@RequestParam(value="minute_end", required=false) Integer minuteEnd,
			@RequestParam(value="email") String email,
			@RequestParam(value="phone_ddd", required=false) Integer phoneDdd,
			@RequestParam(value="phone_number", required=false) String phoneNumber,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="local_id") Integer localId,
			@RequestParam(value="tag_ids[]", required=false) Integer[] tagIds) {
		
		try {
			
			if (localId == 0)
				response.sendRedirect("/pmr2490/events/" + id + "/edit?local_missing");
			else {
			
				DateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");
				
				Date startDate = df.parse(dateStartString + " " + hourStart + ":" + minuteStart);
				
				Date endDate = null;
				if (!dateEndString.equals("") && hourEnd != null && minuteEnd != null)
					endDate = df.parse(dateEndString + " " + hourEnd + ":" + minuteEnd);
				
				phoneNumber = phoneNumber.equals("") ? null : phoneNumber;
				
				Local local = this.localService.get(localId);
				
				List<Tag> tags = new ArrayList<Tag>();
				for(int tagId : tagIds)
					tags.add(this.tagService.get(tagId));
				
				this.eventService.update(id, name, startDate, endDate, email, phoneDdd, phoneNumber, description, local, tags);
				
				response.sendRedirect("/pmr2490/events");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
