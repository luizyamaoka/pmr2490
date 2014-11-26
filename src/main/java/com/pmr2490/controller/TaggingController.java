package com.pmr2490.controller;

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
	public String insert(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("tagId") Integer tagId,
			@RequestParam("eventId") Integer eventId) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Event event = this.eventService.get(eventId);
			
			if (!event.getCreator().getEmail().equals(email))
				return "error/403";
			else if (tagId == null)
				return "redirect:/events/" + event.getId() + "/edit-tags";
			else {
				Tag tag = this.tagService.get(tagId);
				this.taggingService.create(event, tag);
				return "redirect:/events/" + event.getId() + "?tag_created";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Event event = this.taggingService.get(id).getEvent();
			
			if (!event.getCreator().getEmail().equals(email))
				return "error/403";
			else {
				this.taggingService.delete(id);
				return "redirect:/events/" + event.getId() + "?tag_deleted";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
}
