package com.spring.jobPortal.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    @NotBlank
    @Size(min = 5, max = 20)
    private String role;

    @NotBlank
    @Size(min = 5, max = 20)
    private String location;

    @Min(2)
    @Max(60)
    private int experience;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "job")
    List<Application> applications = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "created_by_employee_id", nullable = false)
//    private Employee createdBy;


}
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Job {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int jobId;
//
//    @NotBlank
//    @Size(min = 5, max = 50)
//    private String title;
//
//    @NotBlank
//    private String location;
//
//    @Min(0)
//    @Max(60)
//    private int experience;
//
//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
//
//    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Application> applications = new ArrayList<>();
//}

