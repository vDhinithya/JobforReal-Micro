package com.jobportal.job.repository;

import com.jobportal.job.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByPostedByUserId(String postedByUserId);
}