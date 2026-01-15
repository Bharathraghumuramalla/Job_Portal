package com.spring.jobPortal.Repository_package;

import com.spring.jobPortal.Entity.Application;
import com.spring.jobPortal.Entity.Job;
import com.spring.jobPortal.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>
{
    boolean existsByStudentAndJob(Student studentId, Job jobId);

    Page<Application> findByStudent_StudentId(int id, Pageable p);

    Page<Application> findByJob_JobId(int id, Pageable p);


}
