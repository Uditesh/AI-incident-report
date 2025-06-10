package com.incident_reports.incident.reports.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.incident_reports.incident.reports.Entity.Incident;
import com.incident_reports.incident.reports.Repository.IncidentRepository;
import com.incident_reports.incident.reports.Service.IncidentService;

@Service
public class IncidentServiceImpl implements IncidentService{
	
	AITriageService aiTriageService;
	IncidentRepository incidentRepository;

	/*
	 * public IncidentServiceImpl(AITriageService aiTriageService) { super();
	 * this.aiTriageService = aiTriageService; }
	 */

	public IncidentServiceImpl(AITriageService aiTriageService, IncidentRepository incidentRepository) {
		super();
		this.aiTriageService = aiTriageService;
		this.incidentRepository = incidentRepository;
	}



	@Override
	public Incident createIncident(Incident incident) {
		
		String aiSeverity = aiTriageService.getSeverity(incident.getTitle(),
				incident.getDescription(), incident.getAffectedServices());
		
		String aiCategory = aiTriageService.getCategory(incident.getTitle(),
				incident.getDescription(), incident.getAffectedServices());
		
		incident.setAiSeverity(aiSeverity);
		incident.setAiCategory(aiCategory);
		
		incidentRepository.save(incident);
		return null;
	}

	@Override
	public List<Incident> getAllIncidents() {
		
		return incidentRepository.findAll();
	}

	@Override
	public Optional<Incident> getIncidentById(Long id) {
		
		return incidentRepository.findById(id);
	}

}
