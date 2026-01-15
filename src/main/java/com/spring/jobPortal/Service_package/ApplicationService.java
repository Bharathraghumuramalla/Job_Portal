package com.spring.jobPortal.Service_package;

import com.spring.jobPortal.DTO.ApplicationCreateDTO;
import com.spring.jobPortal.DTO.ApplicationGetDTO;
import com.spring.jobPortal.DTO.UpdateAppDTO;
import com.spring.jobPortal.Entity.*;
import com.spring.jobPortal.Exception.BadRequestException;
import com.spring.jobPortal.Exception.DuplicateException;
import com.spring.jobPortal.Exception.ResourceNotFoundException;
import com.spring.jobPortal.Repository_package.ApplicationRepository;
import com.spring.jobPortal.Repository_package.EmployeeRepository;
import com.spring.jobPortal.Repository_package.JobRepository;
import com.spring.jobPortal.Repository_package.StudentRepository;
import com.spring.jobPortal.SpringSecurity.MyUser;
import com.spring.jobPortal.SpringSecurity.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class ApplicationService
{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    MyUserRepository myUserRepository;

    public void overrideStatus(int id, UpdateAppDTO status)
    {

        Application app = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        if(app.getFinalResult() == ApplicationStatus.PENDING)
        {
            throw new IllegalStateException("Cannot override before employee review");
        }
        if(app.isOverridden() == true) {
            throw new IllegalStateException("Application already overridden");
        }
        if (status.getFinalResult() == ApplicationStatus.PENDING) {
            throw new IllegalArgumentException("Admin cannot set status back to PENDING");
        }

        app.setFinalResult(status.getFinalResult());
        app.setOverridden(true);
        applicationRepository.save(app);
    }
    public Page<ApplicationGetDTO> getAllApplications(int pageNo, int pageSize, String sortBy, String sortOrder)
    {
        Set<String> sortFields = Set.of("studentId", "employeeId", "applicationId");
        if(!sortFields.contains(sortBy))
        {
            throw new BadRequestException("Invalid sort field: " + sortBy);
        }
        if(!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))
        {
            throw new BadRequestException("Invalid sort order: " + sortOrder);
        }
        //Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Sort sort;
        if(sortOrder.equalsIgnoreCase("ASC"))
        {
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }
        int safePage = Math.max(0, pageNo-1);

        Page<Application> app =  applicationRepository.findAll(PageRequest.of(safePage, pageSize, sort));

        return app.map(application -> {
            ApplicationGetDTO A = new ApplicationGetDTO();

            A.setStudentId(application.getStudent().getStudentId());
            A.setApplicationId(application.getApplicationId());
            A.setJobId(application.getJob().getJobId());
            A.setAppliedDate(application.getAppliedDate());
            A.setCurrentStatus(application.getCurrentStatus().toString());
            A.setFinalResult(application.getFinalResult().toString());
            return A;
        });
    }
    public void UpdateApplication(UpdateAppDTO uad, int id)
    {
        Application A = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("application not found"));

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        MyUser user = myUserRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Employee emp = employeeRepository.findByUser(user).orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

        A.setFinalResult(uad.getFinalResult());
        A.setReviewedBy(emp);
        applicationRepository.save(A);
    }
    public void addApp(ApplicationCreateDTO adto)
    {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        MyUser user = myUserRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Student s = studentRepository.findByUser(user).orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        Job j = jobRepository.findById(adto.getJobId()).orElseThrow(((() -> new ResourceNotFoundException("Job Not Found"))));
        if(applicationRepository.existsByStudentAndJob(s,j))
        {
            throw new DuplicateException("Student already applied for this job");
        }

        Application ap = new Application();

        ap.setStudent(s);
        ap.setJob(j);
        ap.setCurrentStatus(ApplicationStatus.APPLIED);
        ap.setAppliedDate(LocalDate.now());
        ap.setFinalResult(ApplicationStatus.PENDING);
        applicationRepository.save(ap);
    }
}
