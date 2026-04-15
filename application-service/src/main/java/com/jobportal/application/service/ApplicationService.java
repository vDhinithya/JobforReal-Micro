package com.jobportal.application.service;

import com.jobportal.application.client.JobClient;
import com.jobportal.application.entity.Application;
import com.jobportal.application.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private JobClient jobClient;

    public Application applyToJob(String jobId, String userId) {
        // CALLING JOB-SERVICE VIA FEIGN
        try {
            jobClient.getJobById(jobId);
        } catch (Exception e) {
            throw new RuntimeException("Job not found! You cannot apply.");
        }

        Application app = new Application();
        app.setJobId(jobId);
        app.setApplicantId(userId);
        app.setStatus("APPLIED");
        return repository.save(app);
    }
}
