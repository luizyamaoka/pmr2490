package com.pmr2490.model;

import java.util.ArrayList;
import java.util.Calendar;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.pmr2490.dto.EventDto;

@Entity
@Table(name=DomainConstants.TB_EVENT)
public class Event {

	/** 
	 * Default Constructor
	 */
	public Event() { }
	
	public Event(Integer id, String name, Date dateStart, Date dateEnd, String email,
			Integer phoneDdd, String phoneNumber, String description, User creator,
			Local local, boolean isApproved) {
		if(id != null) this.id = id;
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.email = email;
		this.phoneDdd = phoneDdd;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.creator = creator;
		this.local = local;
		this.taggings = new ArrayList<Tagging>();
		this.isApproved = isApproved;
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
	
	@Column(name="aprovado")
	private boolean isApproved;
	
	@ManyToOne
	@JoinColumn(name="criado_por")
	private User creator;
	
	@ManyToOne
	@JoinColumn(name="local_id")
	private Local local;
	
	@OneToMany(mappedBy="event")
	@Cascade(CascadeType.DELETE)
	private List<Participant> participants;
	
	@OneToMany(mappedBy="event")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
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
	
	public boolean isApproved() {
		return isApproved;
	}
	
	public void setApproved(boolean isApproved){
		this.isApproved=isApproved;
	}
	
	public EventDto toEventDto() {
		EventDto eventDto = new EventDto();
		
		eventDto.setId(this.id);
		eventDto.setName(this.name);
		eventDto.setCreatorId(this.creator.getId());
		
		if (this.dateStart != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(this.dateStart);
		    eventDto.setDayStart(cal.get(Calendar.DAY_OF_MONTH));
		    eventDto.setMonthStart(cal.get(Calendar.MONTH) + 1);
		    eventDto.setYearStart(cal.get(Calendar.YEAR));
		    eventDto.setHourStart(cal.get(Calendar.HOUR_OF_DAY));
		    eventDto.setMinuteStart(cal.get(Calendar.MINUTE));
		}
		if (this.dateEnd != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(this.dateEnd);
		    eventDto.setDayEnd(cal.get(Calendar.DAY_OF_MONTH));
		    eventDto.setMonthEnd(cal.get(Calendar.MONTH) + 1);
		    eventDto.setYearEnd(cal.get(Calendar.YEAR));
		    eventDto.setHourEnd(cal.get(Calendar.HOUR_OF_DAY));
		    eventDto.setMinuteEnd(cal.get(Calendar.MINUTE));
		}
		
		eventDto.setPhoneDdd(this.phoneDdd);
		eventDto.setPhoneNumber(this.phoneNumber);
		eventDto.setEmail(this.email);
		eventDto.setDescription(this.description);
		
		if (this.local != null) eventDto.setLocalId(this.local.getId());
		if (this.taggings != null) {
			Integer[] tagIds = new Integer[this.taggings.size()]; 
			for (int i = 0; i < this.taggings.size(); i++)
				tagIds[i] = this.taggings.get(i).getTag().getId();
			eventDto.setTagIds(tagIds);
		}

		return eventDto;
	}
	
}
