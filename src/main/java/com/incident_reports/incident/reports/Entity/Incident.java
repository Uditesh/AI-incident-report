package com.incident_reports.incident.reports.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Incident {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable = false)
	private String title;
	
	private String description;
	
	@Column(name = "ai_category")
	private String aiCategory;
	
	@Column(name = "ai_severity")
	private String aiSeverity;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "affected_service")
	private String affectedServices;

	
	public Incident() {
		this.createdAt = LocalDateTime.now();
	}

	/*
	 * public Incident(Long id, String title, String description, String aiCategory,
	 * String aiSeverity) { super(); Id = id; this.title = title; this.description =
	 * description; this.aiCategory = aiCategory; this.aiSeverity = aiSeverity; }
	 */
	
	
	
	public Incident(String title, String description, String affectedServices) {
		super();
		this.title = title;
		this.description = description;
		this.affectedServices = affectedServices;
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public String getAiCategory() {
		return aiCategory;
	}

	public void setAiCategory(String aiCategory) {
		this.aiCategory = aiCategory;
	}

	public String getAiSeverity() {
		return aiSeverity;
	}

	public void setAiSeverity(String aiSeverity) {
		this.aiSeverity = aiSeverity;
	}
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	

	public String getAffectedServices() {
		return affectedServices;
	}

	public void setAffectedServices(String affectedServices) {
		this.affectedServices = affectedServices;
	}

	@Override
	public String toString() {
		return "Incident [Id=" + Id + ", title=" + title + ", description=" + description + ", aiCategory=" + aiCategory
				+ ", aiSeverity=" + aiSeverity + ", createdAt=" + createdAt + ", affectedServices=" + affectedServices
				+ "]";
	}
	
	
}
