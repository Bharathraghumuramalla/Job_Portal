package com.spring.jobPortal.Repository_package;

import com.spring.jobPortal.Entity.Employee;
import com.spring.jobPortal.Entity.Job;
import com.spring.jobPortal.SpringSecurity.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer>
{
    Page<Job> findByLocationIgnoreCase(String location, Pageable pageable);

    Page<Job> findByEmployee_EmployeeId(int id,  Pageable pageable);




}
