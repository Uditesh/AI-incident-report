package com.incident_reports.incident.reports.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incident_reports.incident.reports.Dto.IncidentDto;
import com.incident_reports.incident.reports.Entity.Incident;
import com.incident_reports.incident.reports.Service.IncidentService;

@RestController
@RequestMapping("/api")
public class IncidentController {
	
	IncidentService incidentService;
	
	public IncidentController(IncidentService incidentService) {
		super();
		this.incidentService = incidentService;
	}

	@PostMapping("/incidents")
	public ResponseEntity<Incident> createIncident(@RequestBody IncidentDto incidentDto){
		
		try {
			Incident incident = new Incident(
					incidentDto.getTitle(),
					incidentDto.getDescription(),
					incidentDto.getAffectedService()
					);
			
			Incident createdIncident = incidentService.createIncident(incident);
			return new ResponseEntity<>(createdIncident,HttpStatus.CREATED);
			
		}catch(Exception e) {
			System.out.println("Exception while creating Incident! "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/incidents")
	public ResponseEntity<List<Incident>> getAllIncidents(){
		
		try {
			List<Incident> incidents = incidentService.getAllIncidents();
			return new ResponseEntity<>(incidents,HttpStatus.OK);
			
		}catch(Exception e) {
			System.out.println("Exception while creating Incident! "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/incidents/{id}")
	public ResponseEntity<Incident> getAllIncidents(@PathVariable Long id){
		
		try {
			Optional<Incident> incident = incidentService.getIncidentById(id);
			return new ResponseEntity<>(incident.get(),HttpStatus.OK);
			
		}catch(Exception e) {
			System.out.println("Exception while creating Incident! "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
