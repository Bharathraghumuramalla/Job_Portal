package com.spring.jobPortal.Repository_package;

import com.spring.jobPortal.Entity.Student;
import com.spring.jobPortal.SpringSecurity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
    Student findByStudentName(String studentName);

    Optional<Student> findByUser(MyUser user);
}
