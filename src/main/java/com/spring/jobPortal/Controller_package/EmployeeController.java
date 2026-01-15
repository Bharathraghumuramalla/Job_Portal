package com.spring.jobPortal.Controller_package;

import com.spring.jobPortal.DTO.*;
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
public class EmployeeController
{
    @Autowired
    ApplicationService applicationService;

    @Autowired
    JobService jobService;

    @Autowired
    StudentService studentService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("employee/jobs")
    public ResponseEntity<String> addJob(@Valid @RequestBody JobCreateDTO job)
    {
        jobService.addJob(job);
        return ResponseEntity.status(HttpStatus.OK).body("successfully saved");
    }


    @PreAuthorize("hasAnyRole('ADMIN, EMPLOYEE')")
    @GetMapping("/jobs/student/{studentId}")
    public ResponseEntity<Page<JobResponseDTO>> getJobAppliedByStudent(@PathVariable int studentId,
                                                                       @RequestParam(defaultValue = "1") int pageNo,
                                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                                       @RequestParam(defaultValue = "applicationId") String sortBy,
                                                                       @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        return ResponseEntity.ok(jobService.getJobByStudent(studentId, pageNo, pageSize, sortBy, sortOrder));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/students/job/{jobId}")
    public ResponseEntity<Page<StudentResponseDTO>> getStudentsAppliedForJob(@PathVariable int jobId,
                                                                             @RequestParam(defaultValue = "1") int pageNo,
                                                                             @RequestParam(defaultValue = "10") int pageSize,
                                                                             @RequestParam(defaultValue = "applicationId") String sortBy,
                                                                             @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        return ResponseEntity.ok(studentService.getStudentsAppliedForJob(jobId,pageNo, pageSize, sortBy, sortOrder ));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/employee/applications/{applicationId}/review")
    public ResponseEntity<Object> updateApp(@PathVariable int applicationId, @RequestBody UpdateAppDTO uad)
    {
        applicationService.UpdateApplication(uad, applicationId);
        return ResponseEntity.status(HttpStatus.OK).body("updated successfully");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/applications")
    public ResponseEntity<Page<ApplicationGetDTO>> getAllApplications(@RequestParam(defaultValue = "1") int pageNo,
                                                                      @RequestParam(defaultValue = "10") int pageSize,
                                                                      @RequestParam(defaultValue = "applicationId") String sortBy,
                                                                      @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        return ResponseEntity.ok(applicationService.getAllApplications(pageNo, pageSize, sortBy, sortOrder));
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE, ADMIN')")
    @GetMapping("/students")
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(@RequestParam(defaultValue = "1") int pageNo,
                                                                @RequestParam(defaultValue = "10") int pageSize,
                                                                @RequestParam(defaultValue = "studentId") String sortBy,
                                                                @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        return ResponseEntity.ok(studentService.getStudents(pageNo, pageSize, sortBy, sortOrder ));
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/student/{studentId}")
    public ResponseEntity<StudentResponseDTO>getStudentById(@PathVariable int studentId)
    {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }
}
