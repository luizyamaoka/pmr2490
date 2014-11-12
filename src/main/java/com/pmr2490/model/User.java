package com.pmr2490.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name=DomainConstants.TB_USER)
public class User {

	public User() { }
	
	public User(Integer id, String firstName, String lastName, Date birthDate, String genre, Integer phoneDdd, 
			String phoneNumber, String email, String password, boolean isPromoter, College college, Profession profession){
		if(id != null) this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.genre = genre;
		this.phoneDdd = phoneDdd;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.isPromoter = isPromoter;
		this.college = college;
		this.profession = profession;
	}
	
	public User(Integer id, String firstName, String lastName, Date birthDate, String genre, Integer phoneDdd, 
			String phoneNumber, String email, boolean isPromoter, College college, Profession profession){
		if(id != null) this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.genre = genre;
		this.phoneDdd = phoneDdd;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isPromoter = isPromoter;
		this.college = college;
		this.profession = profession;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nome")
	private String firstName;
	
	@Column(name="sobrenome")
	private String lastName;
	
	@Column(name="data_nascimento")
	private Date birthDate;
	
	@Column(name="sexo")
	private String genre;
	
	@Column(name="ddd")
	private Integer phoneDdd;
	
	@Column(name="telefone")
	private String phoneNumber;
	
	@Column(name="email")
	private String email;
	
	@Column(name="senha")
	private String password;
	
	@Column(name="promotor")
	private boolean isPromoter;
	
	@ManyToOne
	@JoinColumn(name="faculdade_id")
	private College college;
	
	@ManyToOne
	@JoinColumn(name="ocupacao_id")
	private Profession profession;
	
	@OneToMany(mappedBy="creator")
	private List<Event> events;
	
	@OneToMany(mappedBy="user")
	private List<Participant> participations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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

	public boolean isPromoter() {
		return isPromoter;
	}

	public void setPromoter(boolean isPromoter) {
		this.isPromoter = isPromoter;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Participant> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participant> participations) {
		this.participations = participations;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
