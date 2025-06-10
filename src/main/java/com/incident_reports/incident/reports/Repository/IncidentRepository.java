package com.incident_reports.incident.reports.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incident_reports.incident.reports.Entity.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

}
