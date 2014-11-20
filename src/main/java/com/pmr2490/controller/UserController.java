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

import com.pmr2490.dto.UserDto;
import com.pmr2490.model.College;
import com.pmr2490.model.Profession;
import com.pmr2490.model.User;
import com.pmr2490.service.CollegeService;
import com.pmr2490.service.ProfessionService;
import com.pmr2490.service.UserService;

@Controller
@RequestMapping(value="/users")
public class UserController {

	private UserService userService;
	private ProfessionService professionService;
	private CollegeService collegeService;
	
	@Autowired
	public UserController(UserService userService, ProfessionService professionService, 
			CollegeService collegeService) {
		this.userService = userService;
		this.professionService = professionService;
		this.collegeService = collegeService;
	}
	
	@RequestMapping(value="")
	public ModelAndView index() {
		Map<String, Object> map = new HashMap<>();
		map.put("users", userService.getAll());
		return new ModelAndView("user/index", map);
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView show(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("user/show");
		modelAndView.addObject("user", this.userService.get(id));
		return modelAndView;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile() {
		ModelAndView modelAndView = new ModelAndView("user/show");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		modelAndView.addObject("user", this.userService.getByEmail(email));
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}/destroy", method=RequestMethod.POST)
	public void destroy(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
		
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {
				this.userService.delete(id);
				response.sendRedirect("/pmr2490/users");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/{id}/edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int id) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {
				ModelAndView modelAndView = new ModelAndView("user/edit");
				modelAndView.addObject("user", this.userService.get(id));
				modelAndView.addObject("colleges", this.collegeService.getAll());
				modelAndView.addObject("professions", this.professionService.getAll());
				
				if(request.getParameter("passwords_not_matching") != null)
					modelAndView.addObject("error_message", "<strong>Erro!</strong> A senha e sua confirmação devem ser iguais.");
				return modelAndView;
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("error/403");
	}
	
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable int id,
			@RequestParam(value="first_name") String firstName,
			@RequestParam(value="last_name") String lastName,
			@RequestParam(value="birth_day", required=false) Integer birthDay,
			@RequestParam(value="birth_month", required=false) Integer birthMonth,
			@RequestParam(value="birth_year", required=false) Integer birthYear,
			@RequestParam(value="genre", required=false) String genre,
			@RequestParam(value="phone_ddd", required=false) Integer phoneDdd,
			@RequestParam(value="phone_number", required=false) String phoneNumber,
			@RequestParam(value="email") String email,
			@RequestParam(value="college_id", required=false) Integer collegeId,
			@RequestParam(value="profession_id") Integer professionId) {
		
		try {
			String accessEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(accessEmail);
		
			// if user is trying to access the edit page from another user
			if (user.getId() != id) 
				response.sendRedirect("/pmr2490/403");
			else {

				Date birthDate = null;
				if (birthDay != null && birthMonth != null && birthYear != null) {
					Calendar cal = Calendar.getInstance();
					cal.set(birthYear, birthMonth-1, birthDay);
					birthDate = cal.getTime();
				}
				
				phoneNumber = phoneNumber.equals("") ? null : phoneNumber;
				genre = genre.equals("0") ? null : genre;
				
				College college = collegeId == null ? null : this.collegeService.get(collegeId);
				Profession profession = this.professionService.get(professionId);
				
				this.userService.update(id, firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, false, college, profession, null);
			
				response.sendRedirect("/pmr2490/users");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/edit-password")
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
	
	@RequestMapping(value="/update-password")
	public void updatePassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("password") String password,
			@RequestParam("new_password") String newPassword,
			@RequestParam("new_password_confirmation") String newPasswordConfirmation) {
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = this.userService.getByEmail(email);
			
			if (!user.isPasswordCorrect(password))
				response.sendRedirect("/pmr2490/users/edit-password?wrong_password");
			else if (!newPassword.equals(newPasswordConfirmation))
				response.sendRedirect("/pmr2490/users/edit-password?passwords_not_matching");
			else {
				this.userService.update(user.getId(), null, null, null, null, null, null, null, null, null, null, newPassword);
				response.sendRedirect("/pmr2490/users/edit-password?success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
    public String submitForm(@Valid UserDto userDto, BindingResult result, Model m, HttpServletResponse response) {
		
		List<String> errors = new ArrayList<String>();
		
		if(userDto.getFirstName().isEmpty())
			errors.add("O nome precisa ser preenchido.");
		if(userDto.getLastName().isEmpty())
			errors.add("O sobrenome precisa ser preenchido.");
		if(userDto.getEmail().isEmpty())
			errors.add("O email precisa ser preenchido.");
		if(userDto.getPassword().isEmpty())
			errors.add("A senha precisa ser preenchido.");
		if(userDto.getPasswordConfirmation().isEmpty())
			errors.add("A confirmação da senha precisa ser preenchido.");
		if (userDto.getProfessionId().toString().isEmpty())
			errors.add("O campo ocupação precisa ser preenchido.");
		if (!userDto.isPasswordConfirmed())
			errors.add("A senha e a confirmação precisam ser iguais.");
		int phoneNumberLength = userDto.getPhoneNumber().length();
		if (phoneNumberLength != 0 && (phoneNumberLength < 8 || phoneNumberLength > 9))
			errors.add("O numero de telefone deve ter 8 ou 9 caracteres");
		if(this.userService.getByEmail(userDto.getEmail()) != null)
			errors.add("Este e-mail já possui um cadastro");
		
		userDto.setProfessionService(this.professionService);
		userDto.setCollegeService(this.collegeService);
		User user = userDto.toUser();
		
		if (errors.isEmpty()) {
			try {
				
				if (phoneNumberLength == 0)
					userDto.setPhoneNumber(null);
				
				Date birthDate = null;
				if (userDto.getBirthDay() != null && userDto.getBirthMonth() != null && userDto.getBirthYear() != null) {
					Calendar cal = Calendar.getInstance();
					cal.set(userDto.getBirthYear(), userDto.getBirthMonth()-1, userDto.getBirthDay());
					birthDate = cal.getTime();
				}
				College college = userDto.getCollegeId() == null ? null : this.collegeService.get(userDto.getCollegeId());
				Profession profession = userDto.getProfessionId() == null ? null : this.professionService.get(userDto.getProfessionId());
				this.userService.create(userDto.getFirstName(), userDto.getLastName(), birthDate, 
						userDto.getGender(), userDto.getPhoneDdd(), userDto.getPhoneNumber(), 
						userDto.getEmail(), userDto.getPassword(), false, college, profession);
			}
			catch(Exception e) {
				errors.add("Um erro inesperado ocorreu ao efetuar o cadastro. Por favor tente novamente ou contacte o responsavel.");
			}
		}
		
        if(!errors.isEmpty()) {
        	m.addAttribute("errors", errors);
        	m.addAttribute("professions", this.professionService.getAll());
     		m.addAttribute("colleges", this.collegeService.getAll());
            return "user/form";
        }
         
        m.addAttribute("success_message", "Usuário cadastrado com sucesso");
        return "static-pages/login";
    }
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
    public ModelAndView form(Model m) {
		ModelAndView modelAndView = new ModelAndView("user/form");
		modelAndView.addObject("userDto", new UserDto());
		modelAndView.addObject("professions", this.professionService.getAll());
		modelAndView.addObject("colleges", this.collegeService.getAll());
        return modelAndView;
    }
	
}
