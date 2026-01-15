# Job Portal

This project is a role-based job portal backend application that manages the interaction between **Employees** (recruiters) and **Students** (job seekers) throughout the hiring process.

## Overview

The Job Portal Management System is a backend-focused application designed to manage job postings and applications with role-based access control. It allows Employees (HR) to post and manage jobs, while Applicants (Students) can browse and apply for jobs. 

This project demonstrates backend engineering fundamentals such as:
* RESTful API design
* Database relationships
* Authentication and Authorization using Spring Boot

## Tech Stack

* **Backend:** Java, Spring Boot
* **Security:** Spring Security (JWT / Basic Authentication)
* **Database:** MySQL
* **ORM:** JPA / Hibernate
* **Build Tool:** Maven
* **API Testing:** Postman

## Features

* **User Authentication:** Secure login and registration.
* **RBAC:** Role-based access control (Employee, Applicant, Admin).
* **Job Management:** Create, update, and delete job postings.
* **Application Tracking:** Monitor application status in real-time.
* **Database Design:** Optimized schema with proper entity relationships.

## Database Design



### Entities

* **User:** Represents both Employees and Applicants.
    * *Fields:* `id`, `username`, `password`, `role`
* **Job:** Created by an Employee.
    * *Relationship:* One Employee can post multiple Jobs.
* **Application:** Acts as a bridge between Job and Applicant.
    * *Details:* Stores status (Pending/Accepted/Rejected) and applied date.

---

## 🔐 Role-Based Access Control (RBAC)

### Permissions

| Role           | Allowed Actions |

| **EMPLOYEE**   | Create, update, delete jobs, view applicants |
| **APPLICANT**  | View jobs, apply for jobs |
| **BOTH**       | Login and access profile endpoints |

### Enforcement
* Implemented using **Spring Security**.
* Authorization handled via role checks (`hasRole`, `@PreAuthorize`).
* Logged-in user details fetched from `SecurityContextHolder`.

---

## 📑 API Endpoints

### Student APIs
* `GET /student/jobs` - View all jobs
* `GET /student/job/location/{location}` - Filter by location
* `POST /student/application` - Apply for a job

### Employee APIs
* `POST /employee/jobs` - Post a new job
* `PUT /employee/applications/{id}/review` - Update application status
* `GET /employee/applications` - View all received applications

### Admin APIs
* `GET /admin/employees` - List all employees
* `PUT /admin/applications/{id}/override` - Admin status override
* `PATCH /admin/student/{id}` - Update student record
