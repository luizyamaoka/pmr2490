package com.pmr2490.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pmr2490.dao.CollegeDao;
import com.pmr2490.dao.ProfessionDao;
import com.pmr2490.dao.UserDao;
import com.pmr2490.dto.UserDto;
import com.pmr2490.helper.Helper;
import com.pmr2490.model.College;
import com.pmr2490.model.Profession;
import com.pmr2490.model.User;
import com.pmr2490.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private CollegeDao collegeDao;
	private ProfessionDao professionDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao, CollegeDao collegeDao, ProfessionDao professionDao) {
		this.userDao = userDao;
		this.collegeDao = collegeDao;
		this.professionDao = professionDao;
	}
	
	@Override
	@Transactional
	public List<User> getAll() throws Exception {
		return this.userDao.getAll();
	}

	@Override
	@Transactional
	public User get(int id) throws Exception {
		return this.userDao.get(id);
	}
	
	@Transactional
	public User getByEmail(String email) throws Exception {
		return this.userDao.getByEmail(email);
	}

	@Transactional
	public User getEagerByEmail(String email) throws Exception {
		return this.userDao.getEagerByEmail(email);
	}
	
	@Override
	@Transactional
	public void delete(int id) throws Exception {
		User user = this.userDao.get(id);
		this.userDao.delete(user);
	}

	@Override
	@Transactional
	public void updatePassword(int id, String password) throws Exception {
		User user = this.userDao.get(id);
		user.setPassword(password);
		this.userDao.update(user);
	}

	@Override
	@Transactional
	public List<String> create(UserDto userDto) throws Exception {
		
		userDto.setPhoneNumber(Helper.removeNonDigits(userDto.getPhoneNumber()));
		
		List<String> status = this.testUserDto(userDto);
		if(userDto.getPassword().isEmpty()) {
			status.set(0, "error");
			status.add("password.required");
		}
		if(userDto.getPasswordConfirmation().isEmpty()) {
			status.set(0, "error");
			status.add("passwordConfirmation.required");
		}
		if (!userDto.isPasswordConfirmed()) {
			status.set(0, "error");
			status.add("password.match");
		}
		if(this.userDao.getByEmail(userDto.getEmail()) != null) {
			status.set(0, "error");
			status.add("email.existant");
		}
		
		if (status.size() == 1) {
			Integer newUserId = this.userDao.create(this.UserDtoToUser(userDto));
			status.add(newUserId.toString());
		}
		
		return status;
	}

	@Override
	@Transactional
	public List<String> update(UserDto userDto) throws Exception {
		
		userDto.setPhoneNumber(Helper.removeNonDigits(userDto.getPhoneNumber()));
		
		List<String> status = this.testUserDto(userDto);
		
		User existantUser = this.userDao.getByEmail(userDto.getEmail());
		if(existantUser != null && userDto.getId() != existantUser.getId()) {
			status.set(0, "error");
			status.add("email.existant");
		}
		
		if (status.size() == 1) {
			this.userDao.update(this.UserDtoToUser(userDto));
			status.add(userDto.getId().toString());
		}
		
		return status;
	}
	
	private List<String> testUserDto(UserDto userDto) throws Exception {
		List<String> status = new ArrayList<String>();
		status.add("success");
		
		if(userDto.getFirstName() == null || userDto.getFirstName().equals("")) {
			status.set(0, "error");
			status.add("firstName.required");
		}
		if(userDto.getLastName() == null || userDto.getLastName().equals("")) {
			status.set(0, "error");
			status.add("lastName.required");
		}
		if(userDto.getEmail() == null || userDto.getEmail().equals("")) {
			status.set(0, "error");
			status.add("email.required");
		}
		if (userDto.getBirthYear() != null && userDto.getBirthYear() > Calendar.getInstance().get(Calendar.YEAR)) {
			status.set(0, "error");
			status.add("birthYear.past");
		}
		if (userDto.getBirthMonth() != null && (userDto.getBirthMonth() < 1 || userDto.getBirthMonth() > 12)) {
			status.set(0, "error");
			status.add("birthMonth.impossible");
		}
		if (userDto.getBirthDay() != null && (userDto.getBirthDay() < 1 || userDto.getBirthDay() > 31)) {
			status.set(0, "error");
			status.add("birthDay.impossible");
		}
		if ((userDto.getBirthYear() != null || userDto.getBirthMonth() != null || userDto.getBirthDay() != null) &&
				(userDto.getBirthYear() == null || userDto.getBirthMonth() == null || userDto.getBirthDay() == null)) {
			status.set(0, "error");
			status.add("birthDate.incomplete");
		}
		if (userDto.getProfessionId() == null) {
			status.set(0, "error");
			status.add("professionId.required");
		}
		int phoneNumberLength = userDto.getPhoneNumber().length();
		if (!userDto.getPhoneNumber().equals("") && (phoneNumberLength < 8 || phoneNumberLength > 9)) {
			status.set(0, "error");
			status.add("phoneNumber.length");
		}
		if ((userDto.getPhoneDdd() != null || !userDto.getPhoneNumber().equals("")) &&
				(userDto.getPhoneDdd() == null || userDto.getPhoneNumber().equals(""))) {
			status.set(0, "error");
			status.add("phoneNumber.incomplete");
		}
		
		return status;
	}
	
	private User UserDtoToUser(UserDto userDto) throws Exception {
		User user = userDto.getId() == null ? new User() : this.userDao.get(userDto.getId());
		
		if (userDto.getFirstName() != null)
			user.setFirstName(userDto.getFirstName());
		if (userDto.getLastName() != null)
			user.setLastName(userDto.getLastName());
		if (userDto.getPhoneDdd() != null && !userDto.getPhoneNumber().equals("")) {
			user.setPhoneDdd(userDto.getPhoneDdd());
			user.setPhoneNumber(userDto.getPhoneNumber());
		}
		if (userDto.getBirthYear() != null &&  userDto.getBirthMonth() != null && userDto.getBirthDay() != null)
			user.setBirthDate(Helper.buildDate(userDto.getBirthYear(), userDto.getBirthMonth(), userDto.getBirthDay(), null, null));
		if (userDto.getEmail() != null)
			user.setEmail(userDto.getEmail());
		if (userDto.getGender() != null)
			user.setGenre(userDto.getGender().equals("") ? null : userDto.getGender());
		if (userDto.getPassword() != null)
			user.setPassword(userDto.getPassword());
		
		College college = userDto.getCollegeId() == null ? null : this.collegeDao.get(userDto.getCollegeId());
		Profession profession = userDto.getProfessionId() == null ? null : this.professionDao.get(userDto.getProfessionId());
		
		user.setCollege(college);
		user.setProfession(profession);
		
		return user;
	}

}
