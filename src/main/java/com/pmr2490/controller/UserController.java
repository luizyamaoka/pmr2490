package com.pmr2490.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

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

import com.pmr2490.dto.UserDto;
import com.pmr2490.model.User;
import com.pmr2490.model.Event;
import com.pmr2490.model.Participant;
import com.pmr2490.service.CollegeService;
import com.pmr2490.service.ProfessionService;
import com.pmr2490.service.UserService;



@Controller
@RequestMapping(value="/users")
public class UserController {

	private UserService userService;
	private ProfessionService professionService;
	private CollegeService collegeService;
	
	private static final Map<String, String> ERROR_MESSAGES;
	static {
		ERROR_MESSAGES = new HashMap<String, String>();
		ERROR_MESSAGES.put("firstName.required", "O nome precisa ser preenchido.");
		ERROR_MESSAGES.put("lastName.required", "O sobrenome precisa ser preenchido.");
		ERROR_MESSAGES.put("email.required", "O email precisa ser preenchido.");
		ERROR_MESSAGES.put("birthYear.past", "O ano de nascimento não pode ser no futuro.");
		ERROR_MESSAGES.put("birthMonth.impossible", "O mês de nascimento é inválido.");
		ERROR_MESSAGES.put("birthDay.impossible", "O dia de nascimento é inválido.");
		ERROR_MESSAGES.put("birthDate.incomplete", "Sua data de nascimento está incompleta.");
		ERROR_MESSAGES.put("professionId.required", "O campo ocupação precisa ser preenchido.");
		ERROR_MESSAGES.put("phoneNumber.length", "O numero de telefone deve ter 8 ou 9 caracteres.");
		ERROR_MESSAGES.put("phoneNumber.incomplete", "O numero de telefone está incompleto.");
		ERROR_MESSAGES.put("password.required", "A senha precisa ser preenchido.");
		ERROR_MESSAGES.put("passwordConfirmation.required", "A confirmação da senha precisa ser preenchido.");
		ERROR_MESSAGES.put("password.match", "A senha e a confirmação precisam ser iguais.");
		ERROR_MESSAGES.put("email.existant", "Este e-mail já possui um cadastro.");
	}
	
	@Autowired
	public UserController(UserService userService, ProfessionService professionService, 
			CollegeService collegeService) {
		this.userService = userService;
		this.professionService = professionService;
		this.collegeService = collegeService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index() {
		try {
			ModelAndView modelAndView = new ModelAndView("user/index");
			modelAndView.addObject("users", this.userService.getAll());
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(HttpServletRequest request, @PathVariable int id) {
		try {
			ModelAndView modelAndView = new ModelAndView("user/show");
			modelAndView.addObject("user", this.userService.get(id));
			if(request.getParameter("edited") != null)
				modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Usuário editado com sucesso.");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile() {
		try {
			ModelAndView modelAndView = new ModelAndView("user/show");
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			modelAndView.addObject("user", this.userService.getByEmail(email));
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
		
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			if (user.getId() != id && !user.isPromoter()) 
				response.sendRedirect("/pmr2490/403");
			else {
				this.userService.delete(id);
				response.sendRedirect("/pmr2490/users");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/edit-password", method=RequestMethod.GET)
	public ModelAndView editPassword(HttpServletRequest request, HttpServletResponse response) {
	
		ModelAndView modelAndView = new ModelAndView("user/edit-password");
		if(request.getParameter("wrong_password") != null)
			modelAndView.addObject("error_message", "<strong>Senha incorreta!</strong> Tente novamente.");
		else if(request.getParameter("passwords_not_matching") != null)
			modelAndView.addObject("error_message", "<strong>Erro!</strong> A nova senha e sua confirmação devem ser iguais.");
		else if(request.getParameter("success") != null)
			modelAndView.addObject("success_message", "<strong>Sucesso!</strong> Senha alterada.");
		return modelAndView;

	}
	
	@RequestMapping(value="/edit-password", method=RequestMethod.POST)
	public String updatePassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("password") String password,
			@RequestParam("new_password") String newPassword,
			@RequestParam("new_password_confirmation") String newPasswordConfirmation) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			if (!user.isPasswordCorrect(password))
				return "redirect:/users/edit-password?wrong_password";
			else if (!newPassword.equals(newPasswordConfirmation))
				return "redirect:/users/edit-password?passwords_not_matching";
			else {
				this.userService.updatePassword(user.getId(), newPassword);
				return "redirect:/users/edit-password?success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
    public String insert(@Valid UserDto userDto, BindingResult result, Model m, HttpServletResponse response) {
		try {
			List<String> status = this.userService.create(userDto);
			
	        if(status.get(0).equals("error")) {
	        	List<String> errors = new ArrayList<String>();
	        	for (int i = 1; i < status.size(); i++)
	        		errors.add(ERROR_MESSAGES.get(status.get(i)));
	        	m.addAttribute("errors", errors);
	        	m.addAttribute("professions", this.professionService.getAll());
	     		m.addAttribute("colleges", this.collegeService.getAll());
	            return "user/new";
	        }
	         
	        return "redirect:/login?user";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
    }
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
    public ModelAndView newUser(Model m) {
		try {
			ModelAndView modelAndView = new ModelAndView("user/new");
			modelAndView.addObject("userDto", new UserDto());
			modelAndView.addObject("professions", this.professionService.getAll());
			modelAndView.addObject("colleges", this.collegeService.getAll());
	        return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
    }
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
    public String update(@PathVariable int id, @Valid UserDto userDto, BindingResult result, Model m, HttpServletResponse response) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				return "error/403";
			
			List<String> status = this.userService.update(userDto);
			
	        if(status.get(0).equals("error")) {
	        	List<String> errors = new ArrayList<String>();
	        	for (int i = 1; i < status.size(); i++)
	        		errors.add(ERROR_MESSAGES.get(status.get(i)));
	        	m.addAttribute("errors", errors);
	        	m.addAttribute("professions", this.professionService.getAll());
	     		m.addAttribute("colleges", this.collegeService.getAll());
	            return "user/edit";
	        }
	         
	        return "redirect:/users/" + status.get(1) + "?edited";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/unexpected-error";
		}
    }
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id, Model m) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				return new ModelAndView("error/403");
			
			ModelAndView modelAndView = new ModelAndView("user/edit");
			modelAndView.addObject("userDto", user.toDto());
			modelAndView.addObject("professions", this.professionService.getAll());
			modelAndView.addObject("colleges", this.collegeService.getAll());
	        return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
    }
	
	@RequestMapping(value="/my-events", method=RequestMethod.GET)
    public ModelAndView myEvents() {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getEagerByEmail(email);
			Date hoje = new Date(); 
			List<Event> myeventsocorridos = new ArrayList<Event>();
			List<Event> myevents = new ArrayList<Event>();
			List<Event> eventsparticipados = new ArrayList<Event>();
			List<Event> eventsparticipadosocorridos = new ArrayList<Event>();
			
			for(Event event : user.getEvents()){
				if(event.getDateEnd()!=null){
					if(event.getDateEnd().after(hoje)==true)
						myevents.add(event);
					else
						myeventsocorridos.add(event);
				}
				else {
					if(event.getDateStart().after(hoje)==true)
						myevents.add(event);
					else
						myeventsocorridos.add(event);
				}
			} for(Participant part : user.getParticipations()){
				if(part.getEvent().getDateEnd()!=null){
					if(part.getEvent().getDateEnd().after(hoje)==true)
						eventsparticipados.add(part.getEvent());
					else
						eventsparticipadosocorridos.add(part.getEvent());
				} else {
					if(part.getEvent().getDateStart().after(hoje)==true)
						eventsparticipados.add(part.getEvent());
					else
						eventsparticipadosocorridos.add(part.getEvent());
				}
			}

			ModelAndView modelAndView = new ModelAndView("event/showmyevents");
			modelAndView.addObject("myevents",myevents );
			modelAndView.addObject("myeventsocorridos", myeventsocorridos);
			modelAndView.addObject("eventsparticipados", eventsparticipados);
			modelAndView.addObject("eventsparticipadosocorridos", eventsparticipadosocorridos);
			modelAndView.addObject("username", user.getEmail());
	        return modelAndView;
	        
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/unexpected-error");
		}
		
	}
}
