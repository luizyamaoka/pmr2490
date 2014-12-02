package com.pmr2490.dto;


public class EventDto {

	public EventDto() { }
	
	private Integer id;
	private String name;
	private Integer dayStart;
	private Integer monthStart;
	private Integer yearStart;
	private Integer hourStart;
	private Integer minuteStart;
	private Integer dayEnd;
	private Integer monthEnd;
	private Integer yearEnd;
	private Integer hourEnd;
	private Integer minuteEnd;
	private String email;
	private Integer phoneDdd;
	private String phoneNumber;
	private String description;
	private Integer creatorId;
	private Integer localId;
	private Integer[] tagIds;
	private boolean isApproved;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getHourStart() {
		return hourStart;
	}
	public void setHourStart(Integer hourStart) {
		this.hourStart = hourStart;
	}
	public Integer getMinuteStart() {
		return minuteStart;
	}
	public void setMinuteStart(Integer minuteStart) {
		this.minuteStart = minuteStart;
	}
	public Integer getHourEnd() {
		return hourEnd;
	}
	public void setHourEnd(Integer hourEnd) {
		this.hourEnd = hourEnd;
	}
	public Integer getMinuteEnd() {
		return minuteEnd;
	}
	public void setMinuteEnd(Integer minuteEnd) {
		this.minuteEnd = minuteEnd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	public Integer getLocalId() {
		return localId;
	}
	public void setLocalId(Integer localId) {
		this.localId = localId;
	}
	public Integer[] getTagIds() {
		return tagIds;
	}
	public void setTagIds(Integer[] tagIds) {
		this.tagIds = tagIds;
	}
	public Integer getDayStart() {
		return dayStart;
	}
	public void setDayStart(Integer dayStart) {
		this.dayStart = dayStart;
	}
	public Integer getMonthStart() {
		return monthStart;
	}
	public void setMonthStart(Integer monthStart) {
		this.monthStart = monthStart;
	}
	public Integer getYearStart() {
		return yearStart;
	}
	public void setYearStart(Integer yearStart) {
		this.yearStart = yearStart;
	}
	public Integer getDayEnd() {
		return dayEnd;
	}
	public void setDayEnd(Integer dayEnd) {
		this.dayEnd = dayEnd;
	}
	public Integer getMonthEnd() {
		return monthEnd;
	}
	public void setMonthEnd(Integer monthEnd) {
		this.monthEnd = monthEnd;
	}
	public Integer getYearEnd() {
		return yearEnd;
	}
	public void setYearEnd(Integer yearEnd) {
		this.yearEnd = yearEnd;
	}
	public boolean isApproved(){
		return isApproved;
	}
	public void setApproved(boolean Approved){
		this.isApproved=Approved;
	}
	
}
