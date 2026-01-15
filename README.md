# Job_Portal
This project is a role-based job portal backend application that manages the interaction between employees (recruiters) and students (job seekers) throughout the hiring process.

 #Overview

The Job Portal Management System is a backend-focused application designed to manage job postings and applications with role-based access control.
It allows Employees (HR) to post and manage jobs, while Applicants (Students) can browse and apply for jobs.
This project demonstrates backend engineering fundamentals such as RESTful API design, database relationships, authentication, and authorization using Spring Boot

#Tech Stack

Backend: Java, Spring Boot
Security: Spring Security (JWT / Basic Authentication)
Database: MySQL
ORM: JPA / Hibernate
Build Tool: Maven
API Testing: Postman

#Features

User authentication and authorization
Role-based access control (Employee, Applicant)
Job posting and management
Job application tracking with status
Secure REST APIs
Clean database design with proper relationships

#Database Design

Entities

User
Represents both Employees and Applicants
Fields: id, username, password, role

Job
Created by an Employee
One Employee can post multiple Jobs

Application
Acts as a bridge between Job and Applicant
Stores application-specific details such as status and applied date

#Role-Based Access Control

Roles
EMPLOYEE
APPLICANT

Permissions

Role	          Allowed Actions
EMPLOYEE	     Create, update, delete jobs, view applicants
APPLICANT	     View jobs, apply for jobs
BOTH	         Login and access permitted endpoints


#Enforcement

Implemented using Spring Security
Authorization handled via role checks (hasRole, @PreAuthorize)
Logged-in user details fetched from SecurityContextHolder

#API Endpoints

Student APIs
GET  /student/jobs
GET  /student/job/location/{location}
GET  /student/job/{jobId}
POST /student/application

Employee APIs
POST /employee/jobs
GET  /jobs/student/{studentId}
GET  /students/job/{jobId}
PUT  /employee/applications/{applicationId}/review
GET  /employee/applications
GET  /students
GET  /employee/student/{studentId}

Admin APIs
GET   /admin/employees
PUT   /admin/applications/{applicationId}/override
PATCH /admin/student/{studentId}
GET   /admin/employee/jobs/{employeeId}

