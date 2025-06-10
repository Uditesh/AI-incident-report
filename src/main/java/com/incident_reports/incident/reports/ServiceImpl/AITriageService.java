package com.incident_reports.incident.reports.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class AITriageService {
	
	public String getSeverity(String title, String Description ,String affectedService) {
		
		String content = (title + " " + Description + " " +affectedService).toLowerCase();
		
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
	
	public String getCategory(String title, String Description ,String affectedService) {
		String content = (title + " " + Description + " " +affectedService).toLowerCase();
		
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
