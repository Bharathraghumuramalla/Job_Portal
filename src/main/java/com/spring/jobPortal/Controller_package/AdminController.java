package com.spring.jobPortal.Controller_package;

import com.spring.jobPortal.DTO.*;
import com.spring.jobPortal.Service_package.ApplicationService;
import com.spring.jobPortal.Service_package.EmployeeService;
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
public class AdminController
{
    @Autowired
    ApplicationService applicationService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    StudentService studentService;

    @Autowired
    JobService jobService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/employees")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmp(@RequestParam(defaultValue = "1") int pageNo,
                                                               @RequestParam(defaultValue = "10") int pageSize,
                                                               @RequestParam(defaultValue = "employeeId") String sortBy,
                                                               @RequestParam(defaultValue = "ASC") String sortOrder
    )
    {
        return ResponseEntity.ok(employeeService.getAllEmp(pageNo, pageSize, sortBy, sortOrder));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/applications/{applicationId}/override")
    public ResponseEntity<String> override(@PathVariable int applicationId, @RequestBody UpdateAppDTO status)
    {
        applicationService.overrideStatus(applicationId, status);
        return ResponseEntity.status(HttpStatus.OK).body("Status override successfully");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/student/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable int studentId, @Valid @RequestBody StudentUpdateDTO st)
    {
        studentService.updateStudent(studentId, st);
        return ResponseEntity.status(HttpStatus.OK).body("Updated successfully");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/employee/jobs/{employeeId}")
    public ResponseEntity<Page<JobResponseDTO>>getJobsPostedByEmp(@PathVariable int employeeId,
                                                                  @RequestParam(defaultValue = "1") int pageNo,
                                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                                  @RequestParam(defaultValue = "jobId") String sortBy,
                                                                  @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        return ResponseEntity.ok(jobService.getJobsPostedByEmp(employeeId, pageNo, pageSize, sortBy, sortOrder));
    }

}
