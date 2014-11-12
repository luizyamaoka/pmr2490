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
@Table(name=DomainConstants.TB_EVENT)
public class Event {

	public Event() { }
	
	public Event(Integer id, String name, Date dateStart, Date dateEnd, String email,
			Integer phoneDdd, String phoneNumber, String description, User creator,
			Local local) {
		if(id != null) this.id = id;
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.setEmail(email);
		this.phoneDdd = phoneDdd;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.creator = creator;
		this.local = local;
	}
	
	public Event(Integer id, String name, Date dateStart, Date dateEnd, String email,
			Integer phoneDdd, String phoneNumber, String description, Local local) {
		if(id != null) this.id = id;
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.setEmail(email);
		this.phoneDdd = phoneDdd;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.local = local;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="nome")
	private String name;
	
	@Column(name="inicio")
	private Date dateStart;
	
	@Column(name="final")
	private Date dateEnd;
	
	@Column(name="email")
	private String email;
	
	@Column(name="ddd")
	private Integer phoneDdd;
	
	@Column(name="telefone")
	private String phoneNumber;
	
	@Column(name="descricao")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="criado_por")
	private User creator;
	
	@ManyToOne
	@JoinColumn(name="local_id")
	private Local local;
	
	@OneToMany(mappedBy="event")
	private List<Participant> participants;
	
	@OneToMany(mappedBy="event")
	private List<Tagging> taggings;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public List<Tagging> getTaggings() {
		return taggings;
	}

	public void setTaggings(List<Tagging> taggings) {
		this.taggings = taggings;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
