package com.pmr2490.dto;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.pmr2490.model.User;
import com.pmr2490.service.CollegeService;
import com.pmr2490.service.ProfessionService;

public class UserDto {
	
	public UserDto() { }
	
	private Integer id;
	private String firstName;
	private String lastName;
	private Integer birthDay;
	private Integer birthMonth;
	private Integer birthYear;
	private String gender;
	private Integer phoneDdd;
	private String phoneNumber;
	private String email;
	private String password;
	private String passwordConfirmation;
	private Integer professionId;
	private Integer collegeId;
	
	private ProfessionService professionService;
	private CollegeService collegeService;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}
	public Integer getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getPhoneDdd() {
		return phoneDdd;
	}
	public void setPhoneDdd(Integer phoneDdd) {
		this.phoneDdd = phoneDdd;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public Integer getProfessionId() {
		return professionId;
	}
	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	public ProfessionService getProfessionService() {
		return professionService;
	}
	public void setProfessionService(ProfessionService professionService) {
		this.professionService = professionService;
	}
	public CollegeService getCollegeService() {
		return collegeService;
	}
	public void setCollegeService(CollegeService collegeService) {
		this.collegeService = collegeService;
	}
	
	public User toUser() {
		User user = new User();
		if(this.id != null) user.setId(this.id);
		user.setFirstName(this.firstName);
		user.setLastName(this.firstName);
		
		Date birthDate = null;
		if (birthDay != null && birthMonth != null && birthYear != null) {
			Calendar cal = Calendar.getInstance();
			cal.set(birthYear, birthMonth-1, birthDay);
			birthDate = cal.getTime();
		}
		user.setBirthDate(birthDate);
		
		user.setGenre(this.gender);
		user.setPhoneDdd(this.phoneDdd);
		user.setPhoneNumber(this.phoneNumber);
		user.setPassword(this.password);
		user.setEmail(this.email);
		
		if(this.professionId != null)
			user.setProfession(this.professionService.get(1));
		if(this.collegeId != null)
			user.setCollege(this.collegeService.get(1));
		
		return user;
	}
	
	public boolean isPasswordConfirmed() {
		return this.password.equals(this.passwordConfirmation);
	}
	
}
