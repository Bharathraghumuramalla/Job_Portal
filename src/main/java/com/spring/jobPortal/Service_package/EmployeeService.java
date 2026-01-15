package com.spring.jobPortal.Service_package;

import com.spring.jobPortal.DTO.ApplicationGetDTO;
import com.spring.jobPortal.DTO.EmployeeResponseDTO;
import com.spring.jobPortal.DTO.StudentResponseDTO;
import com.spring.jobPortal.DTO.UpdateAppDTO;
import com.spring.jobPortal.Entity.Application;
import com.spring.jobPortal.Entity.Employee;
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
public class EmployeeService
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

    public Page<EmployeeResponseDTO> getAllEmp(int pageNo, int pageSize, String sortBy, String sortOrder)
    {
        if (pageSize <= 0) {
            throw new BadRequestException("pageSize must be > 0");
        }

        Set<String> sortFields = Set.of("age","employeeName", "employeeId");
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

        Page<Employee> emp =  employeeRepository.findAll(PageRequest.of(safePage, pageSize, sort));

        return emp.map(em -> {
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setEmployeeId(em.getEmployeeId());
            dto.setEmployeeName(em.getEmployeeName());
            dto.setAge(em.getAge());
            return dto;
        });

    }
}
