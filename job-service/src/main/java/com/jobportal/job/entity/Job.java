package com.jobportal.job.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jobs")
public class Job {

    @Id
    private String id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private List<String> skillsRequired;
    private String salaryRange;
    private Date postedDate = new Date();
}
