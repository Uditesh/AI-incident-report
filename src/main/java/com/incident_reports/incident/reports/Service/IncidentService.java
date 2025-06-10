package com.incident_reports.incident.reports.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.incident_reports.incident.reports.Entity.Incident;

@Service
public interface IncidentService {

	Incident createIncident(Incident incident);
    List<Incident> getAllIncidents();
    Optional<Incident> getIncidentById(Long id);
	
}
