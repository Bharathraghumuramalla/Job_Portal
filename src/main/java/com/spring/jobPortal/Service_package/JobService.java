package com.spring.jobPortal.Service_package;

import com.spring.jobPortal.DTO.JobCreateDTO;
import com.spring.jobPortal.DTO.JobResponseDTO;
import com.spring.jobPortal.Entity.Application;
import com.spring.jobPortal.Entity.Employee;
import com.spring.jobPortal.Entity.Job;
import com.spring.jobPortal.Entity.Student;
import com.spring.jobPortal.Exception.BadRequestException;
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

import java.util.Set;

@Service
public class JobService
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

    public void addJob(JobCreateDTO job)
    {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        MyUser user = myUserRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Employee em = employeeRepository.findByUser(user).orElseThrow(() -> new UsernameNotFoundException("Employee does not exists"));

        Job j = new Job();
        j.setRole(job.getRole());
        j.setExperience(job.getExperience());
        j.setLocation(job.getLocation());
        j.setEmployee(em);

        jobRepository.save(j);
    }
    public Page<JobResponseDTO> getJobByStudent(int id, int pageNo, int pageSize, String sortBy, String sortOrder)
    {
        Student s = studentRepository.findById(id).orElseThrow((() -> new ResourceNotFoundException("Student does not exists with id" + id)));
        Set<String> sortFields = Set.of("applicationId","appliedDate");
        if(!sortFields.contains(sortBy))
        {
            throw new BadRequestException("Invalid sort field: " + sortBy);
        }
        if(!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))
        {
            throw new BadRequestException("Invalid sort order: " + sortOrder);
        }
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        int safePage = Math.max(0, pageNo-1);
        PageRequest p = PageRequest.of(safePage, pageSize, sort);
        Page<Application> app = applicationRepository.findByStudent_StudentId(id, p);
        if(app.isEmpty())
        {
            throw new ResourceNotFoundException("No jobs applied by the student " + id);
        }

        return app.map(a -> {

            JobResponseDTO dto = new JobResponseDTO();

            dto.setJobId(a.getJob().getJobId());
            dto.setExperience(a.getJob().getExperience());
            dto.setRole(a.getJob().getRole());
            dto.setLocation(a.getJob().getLocation());
            return dto;
        });
    }

    public Page<JobResponseDTO> getAllJobs(int pageNo, int pageSize, String sortBy, String sortOrder)
    {
        Set<String> sortFields = Set.of("jobId","location", "experience");
        if(!sortFields.contains(sortBy))
        {
            throw new BadRequestException("Invalid sort field: " + sortBy);
        }
        if(!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))
        {
            throw new BadRequestException("Invalid sort order: " + sortOrder);
        }
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        int safePage = Math.max(0, pageNo-1);
        PageRequest p = PageRequest.of(safePage, pageSize, sort);
        Page<Job> job =  jobRepository.findAll(p);
        if(job.isEmpty())
        {
            throw new ResourceNotFoundException("There are no jobs");
        }

        return job.map(J -> {
            JobResponseDTO dto = new JobResponseDTO();

            dto.setJobId(J.getJobId());
            dto.setRole(J.getRole());
            dto.setLocation(J.getLocation());
            dto.setExperience(J.getExperience());
            return dto;
        });
        //“Since the lambda returns EmployeeResponseDTO,
        //map() returns Page<EmployeeResponseDTO>.”
    }
    public JobResponseDTO GetJobById(int id)
    {
        Job J = jobRepository.findById(id).orElseThrow((() -> new ResourceNotFoundException("Job not found with id " + id)));

        JobResponseDTO dto = new JobResponseDTO();

        dto.setJobId(J.getJobId());
        dto.setRole(J.getRole());
        dto.setLocation(J.getLocation());
        dto.setExperience(J.getExperience());

        return dto;
    }
    public Page<JobResponseDTO> getJobByLocation(String s, int pageNo, int pageSize, String sortBy, String sortOrder)
    {

        Set<String> sortFields = Set.of("jobId","location", "experience");
        if(!sortFields.contains(sortBy))
        {
            throw new BadRequestException("Invalid sort field: " + sortBy);
        }
        if(!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))
        {
            throw new BadRequestException("Invalid sort order: " + sortOrder);
        }
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        int safePage = Math.max(0, pageNo-1);
        PageRequest p = PageRequest.of(safePage, pageSize, sort);
        Page<Job> j =  jobRepository.findByLocationIgnoreCase(s,p);
        if(j.isEmpty())
        {
            throw new ResourceNotFoundException("No job found by this location " + s);
        }
        return j.map(J ->
        {
            JobResponseDTO dto = new JobResponseDTO();

            dto.setJobId(J.getJobId());
            dto.setRole(J.getRole());
            dto.setLocation(J.getLocation());
            dto.setExperience(J.getExperience());
            return dto;
        });

//        for(Job J : j)
//        {
//            JobResponseDTO dto = new JobResponseDTO();
//
//            dto.setJobId(J.getJobId());
//            dto.setRole(J.getRole());
//            dto.setLocation(J.getLocation());
//            dto.setExperience(J.getExperience());
//
//            result.add(dto);
//        }
//        return result;
    }
    public Page<JobResponseDTO> getJobsPostedByEmp(int id,int pageNo, int pageSize, String sortBy, String sortOrder)
    {

        Set<String> sortFields = Set.of("jobId","location", "experience");
        if(!sortFields.contains(sortBy))
        {
            throw new BadRequestException("Invalid sort field: " + sortBy);
        }
        if(!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))
        {
            throw new BadRequestException("Invalid sort order: " + sortOrder);
        }
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        int safePage = Math.max(0, pageNo-1);
        PageRequest p = PageRequest.of(safePage, pageSize, sort);
        Page<Job> job = jobRepository.findByEmployee_EmployeeId(id,p);
        if(job.isEmpty())
        {
            throw new ResourceNotFoundException("Jobs not found");
        }
        return job.map(J ->
        {
            JobResponseDTO dto = new JobResponseDTO();
            dto.setJobId(J.getJobId());
            dto.setRole(J.getRole());
            dto.setLocation(J.getLocation());
            dto.setExperience(J.getExperience());
            return dto;
        });

//        Page<Job> j  =  jobRepository.findByEmployee_EmployeeId(id, p);
//        if(j == null)
//        {
//            throw new ResourceNotFoundException("No jobs were applied by this employee with id " + id);
//        }
//        return j.map(J ->
//        {
//            JobResponseDTO dto = new JobResponseDTO();
//            dto.setJobId(J.getJobId());
//            dto.setRole(J.getRole());
//            dto.setLocation(J.getLocation());
//            dto.setExperience(J.getExperience());
//            return dto;
//        });
//        List<JobResponseDTO> result = new ArrayList<>();

//
//        for(Job J : j)
//        {
//            JobResponseDTO dto = new JobResponseDTO();
//
//            dto.setJobId(J.getJobId());
//            dto.setRole(J.getRole());
//            dto.setLocation(J.getLocation());
//            dto.setExperience(J.getExperience());
//
//            result.add(dto);
//        }
//        return result;
    }


}
