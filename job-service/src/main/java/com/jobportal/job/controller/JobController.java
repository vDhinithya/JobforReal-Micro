package com.jobportal.job.controller;

import com.jobportal.job.entity.Job;
import com.jobportal.job.entity.JobApplication;
import com.jobportal.job.repository.JobApplicationRepository;
import com.jobportal.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @GetMapping("/test")
    public String testJobService() {
        return "Job Service is working! You bypassed the API Gateway Bouncer successfully.";
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job savedJob = jobRepository.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable String id) {
        return jobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/posted-by/{userId}")
    public ResponseEntity<List<Job>> getJobsPostedByUser(@PathVariable String userId) {
        return ResponseEntity.ok(jobRepository.findByPostedByUserId(userId));
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplication> applyForJob(
            @PathVariable String jobId,
            @RequestParam String applicantId) {

        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setApplicantId(applicantId);
        application.setStatus("APPLIED"); // Default status

        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationRepository.save(application));
    }

    @PostMapping("/change-app-status")
    public ResponseEntity<JobApplication> changeApplicationStatus(
            @RequestParam String applicationId,
            @RequestParam String newStatus) {

        return jobApplicationRepository.findById(applicationId)
                .map(app -> {
                    app.setStatus(newStatus);
                    return ResponseEntity.ok(jobApplicationRepository.save(app));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}