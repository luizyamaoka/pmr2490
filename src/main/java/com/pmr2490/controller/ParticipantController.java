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
import com.pmr2490.model.Participant;
import com.pmr2490.model.User;
import com.pmr2490.service.EventService;
import com.pmr2490.service.ParticipantService;
import com.pmr2490.service.UserService;

@Controller
@RequestMapping("/participants")
public class ParticipantController {

	private ParticipantService participantService;
	private UserService userService;
	private EventService eventService;
	
	@Autowired
	public ParticipantController(ParticipantService participantService, UserService userService, 
			EventService eventService) {
		this.participantService = participantService;
		this.userService = userService;
		this.eventService = eventService;
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public void insert(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("eventId") Integer eventId) {
		try {
			
			if (eventId == null)
				response.sendRedirect("/pmr2490/events");
			else {
				String email = SecurityContextHolder.getContext().getAuthentication().getName();
				User user = this.userService.getByEmail(email);
				Event event = this.eventService.get(eventId);
				this.participantService.create(event, user);
				response.sendRedirect("/pmr2490/events/" + eventId);
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
			Participant participant = this.participantService.get(id);
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			
			if (!participant.getUser().getEmail().equals(email))
				response.sendRedirect("/pmr2490/403");
			else {
				this.participantService.delete(id);
				response.sendRedirect("/pmr2490/events/" + participant.getEvent().getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
