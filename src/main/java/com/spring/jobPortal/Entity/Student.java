package com.spring.jobPortal.Entity;

import com.spring.jobPortal.SpringSecurity.MyUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @NotBlank
    @Size(min = 5, max = 300)
    private String studentName;

    @NotBlank
    private String college;

    @Min(18)
    @NotNull
    private Integer age;

    @NotNull
    @Min(1990)
    private Integer yearOfPassing;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "Student_Job",
//               joinColumns = @JoinColumn(name = "Student_id"),
//               inverseJoinColumns = @JoinColumn(name = "Job_id"))
//    List<Job> job = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
    List<Application> applications = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true,nullable = false)
    private MyUser user;



}
