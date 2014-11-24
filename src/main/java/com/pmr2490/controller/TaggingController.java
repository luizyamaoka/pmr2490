package com.pmr2490.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmr2490.model.Event;
import com.pmr2490.model.Tag;
import com.pmr2490.service.EventService;
import com.pmr2490.service.TagService;
import com.pmr2490.service.TaggingService;

@Controller
@RequestMapping("/taggings")
public class TaggingController {

	private TaggingService taggingService;
	private EventService eventService;
	private TagService tagService;
	
	@Autowired
	public TaggingController(TaggingService taggingService, EventService eventService, TagService tagService) {
		this.taggingService = taggingService;
		this.eventService = eventService;
		this.tagService = tagService;
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public void insert(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("tagId") Integer tagId,
			@RequestParam("eventId") Integer eventId) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Event event = this.eventService.get(eventId);
			
			if (!event.getCreator().getEmail().equals(email))
				response.sendRedirect("/pmr2490/403");
			else if (tagId == null)
				response.sendRedirect("/pmr2490/events/" + event.getId() + "/edit-tags");
			else {
				Tag tag = this.tagService.get(tagId);
				this.taggingService.create(event, tag);
				response.sendRedirect("/pmr2490/events/" + event.getId() + "/edit");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Event event = this.taggingService.get(id).getEvent();
			
			if (!event.getCreator().getEmail().equals(email))
				response.sendRedirect("/pmr2490/403");
			else {
				this.taggingService.delete(id);
				response.sendRedirect("/pmr2490/events/" + event.getId() + "/edit");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
