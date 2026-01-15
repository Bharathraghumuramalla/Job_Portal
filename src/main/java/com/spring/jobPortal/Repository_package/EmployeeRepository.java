package com.spring.jobPortal.Repository_package;

import com.spring.jobPortal.Entity.Employee;
import com.spring.jobPortal.SpringSecurity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{
    Optional<Employee> findByUser(MyUser employeeName);
}
