package com.spring.jobPortal.Controller_package;

import com.spring.jobPortal.DTO.ApplicationCreateDTO;
import com.spring.jobPortal.DTO.JobResponseDTO;
import com.spring.jobPortal.Service_package.ApplicationService;
import com.spring.jobPortal.Service_package.JobService;
import com.spring.jobPortal.Service_package.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController
{
    @Autowired
    ApplicationService applicationService;

    @Autowired
    JobService jobService;

    @Autowired
    StudentService studentService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/jobs")
    public ResponseEntity<Page<JobResponseDTO>> getAllJobs(@RequestParam(defaultValue = "1") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize,
                                                           @RequestParam(defaultValue = "jobId") String sortBy,
                                                           @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        return ResponseEntity.ok(jobService.getAllJobs(pageNo, pageSize, sortBy, sortOrder ));
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/job/location/{s}")
    public ResponseEntity<Page<JobResponseDTO>> getJobByLocation(@PathVariable String s,
                                                                 @RequestParam(defaultValue = "1") int pageNo,
                                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                                 @RequestParam(defaultValue = "jobId") String sortBy,
                                                                 @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        String st = s.toUpperCase();
        return ResponseEntity.ok(jobService.getJobByLocation(st, pageNo, pageSize, sortBy, sortOrder));
    }
    @PreAuthorize("hasAnyRole('STUDENT,ADMIN')")
    @GetMapping("/student/job/{jobId}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable int jobId)
    {
        return ResponseEntity.ok(jobService.GetJobById(jobId));
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/student/application")
    public ResponseEntity<String> addApp(@Valid @RequestBody ApplicationCreateDTO adto)
    {
        applicationService.addApp(adto);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully applied");
    }

}
