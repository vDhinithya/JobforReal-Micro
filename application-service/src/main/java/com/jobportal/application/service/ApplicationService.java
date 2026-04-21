package com.jobportal.application.service;

import com.jobportal.application.client.JobClient;
import com.jobportal.application.entity.Application;
import com.jobportal.application.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private JobClient jobClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Application applyToJob(String jobId, String userId) {
        // 1. Verify via OpenFeign
        try {
            jobClient.getJobById(jobId);
        } catch (Exception e) {
            throw new RuntimeException("Job not found! You cannot apply.");
        }

        // 2. Save to MongoDB
        Application app = new Application();
        app.setJobId(jobId);
        app.setApplicantId(userId);
        app.setStatus("APPLIED");
        Application savedApp = repository.save(app);

        // 3. FIRE THE KAFKA EVENT!
        // We are constructing a simple JSON string to send to the broker
        String message = String.format("{\"applicationId\":\"%s\", \"jobId\":\"%s\", \"userId\":\"%s\"}",
                savedApp.getId(), jobId, userId);

        kafkaTemplate.send("notificationTopic", message);
        System.out.println(" KAFKA EVENT FIRED: " + message);

        return savedApp;
    }
}