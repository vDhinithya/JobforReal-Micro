package com.jobportal.application.controller;

import com.jobportal.application.entity.Application;
import com.jobportal.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyForJob(
            @RequestParam String jobId,
            @RequestParam String userId) {
        try {
            Application app = applicationService.applyToJob(jobId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(app);
        } catch (RuntimeException e) {
            // If Feign fails to find the job, it drops down to this catch block
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
