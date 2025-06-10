package com.incident_reports.incident.reports.Dto;

public class IncidentDto {
	
	private String title;
	private String description;
	private String affectedService;
	public IncidentDto() {
		super();
	}
	public IncidentDto(String title, String description, String affectedService) {
		super();
		this.title = title;
		this.description = description;
		this.affectedService = affectedService;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAffectedService() {
		return affectedService;
	}
	public void setAffectedService(String affectedService) {
		this.affectedService = affectedService;
	}
	@Override
	public String toString() {
		return "IncidentDto [title=" + title + ", description=" + description + ", affectedService=" + affectedService
				+ "]";
	}
	
	
}
