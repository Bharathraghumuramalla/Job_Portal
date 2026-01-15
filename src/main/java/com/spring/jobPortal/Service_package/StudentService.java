package com.spring.jobPortal.Service_package;

import com.spring.jobPortal.DTO.StudentResponseDTO;
import com.spring.jobPortal.DTO.StudentUpdateDTO;
import com.spring.jobPortal.Entity.Application;
import com.spring.jobPortal.Entity.Student;
import com.spring.jobPortal.Exception.BadRequestException;
import com.spring.jobPortal.Exception.ResourceNotFoundException;
import com.spring.jobPortal.Repository_package.ApplicationRepository;
import com.spring.jobPortal.Repository_package.EmployeeRepository;
import com.spring.jobPortal.Repository_package.JobRepository;
import com.spring.jobPortal.Repository_package.StudentRepository;
import com.spring.jobPortal.SpringSecurity.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class StudentService
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

    public Page<StudentResponseDTO> getStudentsAppliedForJob(int id, int pageNo, int pageSize, String sortBy, String sortOrder)
    {
        Set<String> sortFields = Set.of("applicationId", "appliedDate");
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
        Page<Application> a = applicationRepository.findByJob_JobId(id, p);
        if(a.isEmpty())
        {
            throw new ResourceNotFoundException("No student applied for this Job");
        }

        return a.map(ap -> {

            StudentResponseDTO st = new StudentResponseDTO();

            st.setStudentId(ap.getStudent().getStudentId());
            st.setStudentName(ap.getStudent().getStudentName());
            st.setAge(ap.getStudent().getAge());
            st.setCollege(ap.getStudent().getCollege());
            st.setEmail(ap.getStudent().getEmail());
            st.setYearOfPassing(ap.getStudent().getYearOfPassing());
            return st;
        });
    }

    public Page<StudentResponseDTO> getStudents(int pageNo, int pageSize, String sortBy, String sortOrder)
    {

        Set<String> sortFields = Set.of("studentId","studentName", "age", "yearOfPassing", "college");
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
        Page<Student> s = studentRepository.findAll(p);
        if(s.isEmpty())
        {
            throw new ResourceNotFoundException("No students found");
        }

        return s.map(S -> {
            StudentResponseDTO dto = new StudentResponseDTO();

            dto.setStudentId(S.getStudentId());
            dto.setStudentName(S.getStudentName());
            dto.setAge(S.getAge());
            dto.setCollege(S.getCollege());
            dto.setEmail(S.getEmail());
            dto.setYearOfPassing(S.getYearOfPassing());

            return dto;
        });
    }
    public StudentResponseDTO getStudentById(int id)
    {
        Student s =  studentRepository.findById(id).orElseThrow((() -> new ResourceNotFoundException("Student Not Found with id " + id)));

        StudentResponseDTO stud = new StudentResponseDTO();

        stud.setStudentId(s.getStudentId());
        stud.setStudentName(s.getStudentName());
        stud.setAge(s.getAge());
        stud.setCollege(s.getCollege());
        stud.setEmail(s.getEmail());
        stud.setYearOfPassing(s.getYearOfPassing());

        return stud;
    }
    public void updateStudent(int id, StudentUpdateDTO dto)
    {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        if (dto.getStudentName() != null) {
            student.setStudentName(dto.getStudentName());
        }

        if (dto.getCollege() != null) {
            student.setCollege(dto.getCollege());
        }

        if (dto.getAge() != null) {
            if (dto.getAge() <= 0) {
                throw new BadRequestException("Age must be positive");
            }
            student.setAge(dto.getAge());
        }

        if (dto.getYearOfPassing() != null) {
            student.setYearOfPassing(dto.getYearOfPassing());
        }

        if (dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }

        studentRepository.save(student);
    }

}
