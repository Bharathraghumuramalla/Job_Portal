package com.spring.jobPortal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "applicationTable",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "job_id"})
        }
)

//ensures that a student will not apply the job twice
public class Application
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne()
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(nullable = false)
    private LocalDate appliedDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private ApplicationStatus CurrentStatus;

    @Enumerated(EnumType.STRING)
   private ApplicationStatus finalResult;

    @ManyToOne()
    @JoinColumn(name = "reviewed_by_employee_id")
    private Employee reviewedBy;

   @Column(nullable = false)
    private boolean overridden = false;
}
