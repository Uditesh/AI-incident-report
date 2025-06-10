# AI based Incident Reporting

## I have developed this application using Java, Spring Boot, H2 DB, and JPA.

### I chode H2 in memory Database for simplicity and quick access. 

## Steps to run and check the endpoints

### 1) clone the project
### 2) Open in ecliplse or spring STS or intelliJ IDE
### 3) run the project
### 4) open Post man to hit APIs
### 5) Select POST as API type, in body, select raw and give input -> 

 {
     "title": "production",
     "description":"Production Down",
     "affectedService": "Security breach"
 }

### 6) hit the POST API -> http://localhost:8080/api/incidents
### 7) hit the GET API -> http://localhost:8080/api/incidents
### 8) in browser, we can check the DB by hitting this URL -> http://localhost:8080/h2-console/
### 9) login into this by giving User Name: sa, Password: password, and JDBC URL: jdbc:h2:mem:testdb
### 10) we can see the INCIDENT table there

## AI assist usage

### Usually I use ChatGPT but today it was down so I tried using claude. 
### I gave the structured input so I can effectively work with it to build the assignement quickly and effectively. 
prompt : let's collab and work on Assignment which is "AI-Powered Incident Triage Assistant"
Context: : Your platform aims to help IT support teams quickly prioritize and categorize incoming service incidents. You'll build the "Event-to-Insight" system where the "events" are incident reports. The system should ingest an incident, apply AI logic to suggest its severity and category, and then either provide an API for its retrieval or display it in a web dashboard. 
I want to achieve this with Java 21, Spring Boot 3.5, JPA, H2. Let's create project structure such that we have Entity class, Repository interface, service interface, service implementation for business logic, controller with Post an Get endpoints.
Expected Output: A working API endpoint (POST
/incidents) that accepts incident data (title, description, affected_service),
processes it through your AI logic to assign ai_severity and ai_category, stores it,
and returns the created incident.  
I don't want complicated app, let's build functional system which fulfills the requirements, so keep it functional, simple and effective .  
I already have created project from spring initializr with dependencies like Spring Data JPA, H2 Database, Spring Web. 

AI suggestion/ response: 
// 1. Entity Class - Incident.java
package com.example.incidenttriage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "affected_service")
    private String affectedService;
    
    @Column(name = "ai_severity")
    private String aiSeverity;
    
    @Column(name = "ai_category")
    private String aiCategory;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Incident() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Incident(String title, String description, String affectedService) {
        this.title = title;
        this.description = description;
        this.affectedService = affectedService;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getAiSeverity() {
        return aiSeverity;
    }
    
    public void setAiSeverity(String aiSeverity) {
        this.aiSeverity = aiSeverity;
    }
    
    public String getAiCategory() {
        return aiCategory;
    }
    
    public void setAiCategory(String aiCategory) {
        this.aiCategory = aiCategory;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

// 2. Repository Interface - IncidentRepository.java
package com.example.incidenttriage.repository;

import com.example.incidenttriage.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByAiSeverity(String aiSeverity);
    List<Incident> findByAiCategory(String aiCategory);
    List<Incident> findByAffectedService(String affectedService);
}

// 3. Service Interface - IncidentService.java
package com.example.incidenttriage.service;

import com.example.incidenttriage.entity.Incident;
import java.util.List;
import java.util.Optional;

public interface IncidentService {
    Incident createIncident(Incident incident);
    List<Incident> getAllIncidents();
    Optional<Incident> getIncidentById(Long id);
    List<Incident> getIncidentsBySeverity(String severity);
    List<Incident> getIncidentsByCategory(String category);
}

// 4. AI Logic Service - AITriageService.java
package com.example.incidenttriage.service;

import org.springframework.stereotype.Service;

@Service
public class AITriageService {
    
    public String determineSeverity(String title, String description, String affectedService) {
        String content = (title + " " + description + " " + affectedService).toLowerCase();
        
        // Critical severity keywords
        if (content.contains("down") || content.contains("outage") || 
            content.contains("critical") || content.contains("emergency") ||
            content.contains("production") || content.contains("security breach") ||
            content.contains("data loss")) {
            return "CRITICAL";
        }
        
        // High severity keywords
        if (content.contains("error") || content.contains("failed") ||
            content.contains("urgent") || content.contains("performance") ||
            content.contains("slow") || content.contains("timeout") ||
            content.contains("database")) {
            return "HIGH";
        }
        
        // Medium severity keywords
        if (content.contains("issue") || content.contains("problem") ||
            content.contains("warning") || content.contains("minor") ||
            content.contains("request")) {
            return "MEDIUM";
        }
        
        // Default to LOW if no specific keywords found
        return "LOW";
    }
    
    public String determineCategory(String title, String description, String affectedService) {
        String content = (title + " " + description + " " + affectedService).toLowerCase();
        
        // Network related
        if (content.contains("network") || content.contains("connection") ||
            content.contains("vpn") || content.contains("internet") ||
            content.contains("connectivity")) {
            return "NETWORK";
        }
        
        // Security related
        if (content.contains("security") || content.contains("breach") ||
            content.contains("virus") || content.contains("malware") ||
            content.contains("unauthorized") || content.contains("hack")) {
            return "SECURITY";
        }
        
        // Database related
        if (content.contains("database") || content.contains("sql") ||
            content.contains("data") || content.contains("backup") ||
            content.contains("storage")) {
            return "DATABASE";
        }
        
        // Application related
        if (content.contains("application") || content.contains("app") ||
            content.contains("software") || content.contains("bug") ||
            content.contains("feature") || content.contains("login")) {
            return "APPLICATION";
        }
        
        // Hardware related
        if (content.contains("hardware") || content.contains("server") ||
            content.contains("memory") || content.contains("disk") ||
            content.contains("cpu") || content.contains("power")) {
            return "HARDWARE";
        }
        
        // Default category
        return "GENERAL";
    }
}

// 5. Service Implementation - IncidentServiceImpl.java
package com.example.incidenttriage.service.impl;

import com.example.incidenttriage.entity.Incident;
import com.example.incidenttriage.repository.IncidentRepository;
import com.example.incidenttriage.service.AITriageService;
import com.example.incidenttriage.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentServiceImpl implements IncidentService {
    
    @Autowired
    private IncidentRepository incidentRepository;
    
    @Autowired
    private AITriageService aiTriageService;
    
    @Override
    public Incident createIncident(Incident incident) {
        // Apply AI logic to determine severity and category
        String aiSeverity = aiTriageService.determineSeverity(
            incident.getTitle(), 
            incident.getDescription(), 
            incident.getAffectedService()
        );
        
        String aiCategory = aiTriageService.determineCategory(
            incident.getTitle(), 
            incident.getDescription(), 
            incident.getAffectedService()
        );
        
        incident.setAiSeverity(aiSeverity);
        incident.setAiCategory(aiCategory);
        
        return incidentRepository.save(incident);
    }
    
    @Override
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }
    
    @Override
    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }
    
    @Override
    public List<Incident> getIncidentsBySeverity(String severity) {
        return incidentRepository.findByAiSeverity(severity);
    }
    
    @Override
    public List<Incident> getIncidentsByCategory(String category) {
        return incidentRepository.findByAiCategory(category);
    }
}

// 6. DTO for Request/Response - IncidentDTO.java
package com.example.incidenttriage.dto;

import jakarta.validation.constraints.NotBlank;

public class IncidentDTO {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotBlank(message = "Affected service is required")
    private String affectedService;
    
    // Constructors
    public IncidentDTO() {}
    
    public IncidentDTO(String title, String description, String affectedService) {
        this.title = title;
        this.description = description;
        this.affectedService = affectedService;
    }
    
    // Getters and Setters
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
}

// 7. Controller - IncidentController.java
package com.example.incidenttriage.controller;

import com.example.incidenttriage.dto.IncidentDTO;
import com.example.incidenttriage.entity.Incident;
import com.example.incidenttriage.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class IncidentController {
    
    @Autowired
    private IncidentService incidentService;
    
    @PostMapping("/incidents")
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody IncidentDTO incidentDTO) {
        try {
            Incident incident = new Incident(
                incidentDTO.getTitle(),
                incidentDTO.getDescription(),
                incidentDTO.getAffectedService()
            );
            
            Incident createdIncident = incidentService.createIncident(incident);
            return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/incidents")
    public ResponseEntity<List<Incident>> getAllIncidents() {
        try {
            List<Incident> incidents = incidentService.getAllIncidents();
            return new ResponseEntity<>(incidents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/incidents/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentService.getIncidentById(id);
        
        if (incident.isPresent()) {
            return new ResponseEntity<>(incident.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/incidents/severity/{severity}")
    public ResponseEntity<List<Incident>> getIncidentsBySeverity(@PathVariable String severity) {
        try {
            List<Incident> incidents = incidentService.getIncidentsBySeverity(severity.toUpperCase());
            return new ResponseEntity<>(incidents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/incidents/category/{category}")
    public ResponseEntity<List<Incident>> getIncidentsByCategory(@PathVariable String category) {
        try {
            List<Incident> incidents = incidentService.getIncidentsByCategory(category.toUpperCase());
            return new ResponseEntity<>(incidents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

// 8. Application Properties - application.yml
# Place this in src/main/resources/application.yml
server:
  port: 8080

spring:
  application:
    name: incident-triage-assistant
  
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  
  h2:
    console:
      enabled: true
      path: /h2-console
  
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true

logging:
  level:
    com.example.incidenttriage: DEBUG
    org.springframework.web: DEBUG

// 9. Main Application Class - IncidentTriageApplication.java
package com.example.incidenttriage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IncidentTriageApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(IncidentTriageApplication.class, args);
    }
}

// 10. Exception Handling - GlobalExceptionHandler.java
package com.example.incidenttriage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), 
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

### My Action : I wanted to build this application with best practices of Java and Seperation of concern design pattern. I also used, abstraction and Dto design patterns. 

### As i hit the limit in claude, I couldn't give more prompts. Later when I wanted to check the data in H2 database, I tried asking to ChatGpt and it was workin. 

Prompt : how to see h2 console in the browser? I have added properties for it in application.properties file for spring boot project. 
file ->
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect 

AI Response: To see the H2 Console in the browser for your Spring Boot project, you need two things:

Correct properties in application.properties

Your Spring Boot application configured to allow web access to the H2 console

✅ Step-by-Step Instructions
✅ 1. Add the following to application.properties
You're almost there. Just add these two missing lines:

properties
Copy
Edit
# Enable the H2 web console
spring.h2.console.enabled=true

# Optional: set path for the console (default is /h2-console)
spring.h2.console.path=/h2-console
So your full config should look like:

properties
Copy
Edit
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Enable H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
✅ 2. Access the H2 console in the browser
Once your Spring Boot app is running, open this URL in your browser:

bash
Copy
Edit
http://localhost:8080/h2-console
If your app is running on a different port, change the URL accordingly.

✅ 3. Connect to the in-memory database
On the login screen:

JDBC URL: jdbc:h2:mem:testdb

User Name: sa

Password: password (or blank if you haven’t set one)

Then click Connect.



 
