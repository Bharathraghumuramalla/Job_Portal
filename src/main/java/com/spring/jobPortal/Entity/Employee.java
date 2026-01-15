package com.spring.jobPortal.Entity;

import com.spring.jobPortal.SpringSecurity.MyUser;
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
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    @NotBlank
    @Size(min = 3, max = 50)
    private String employeeName;

    @Min(19)
    @Max(60)
    private int age;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Job> jobs = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private MyUser user;

    @OneToMany(mappedBy =  "reviewedBy")
    private List<Application> application ;

//    @OneToMany(mappedBy = "createdBy")
//    private List<Student> createdStudents;


}
