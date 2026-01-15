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


    /* Relationships */
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "reviewed_by_employee_id")
    private Employee reviewedBy;

    /* Timeline */
    @Column(nullable = false, updatable = false)
    private LocalDate appliedDate;

    /* Status */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus currentStatus;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus finalResult;

    /* Control flag */
    @Column(nullable = false)
    private boolean overridden = false;
//    @ManyToOne()
//    @JoinColumn(name = "student_id")
//    private Student student;
//
//    @ManyToOne()
//    @JoinColumn(name = "job_id")
//    private Job job;
//
//    @Column(nullable = false)
//    private LocalDate appliedDate = LocalDate.now();
//
//    @Enumerated(EnumType.STRING)
//    private ApplicationStatus CurrentStatus;
//
//    @Enumerated(EnumType.STRING)
//   private ApplicationStatus finalResult;
//
//    @ManyToOne()
//    @JoinColumn(name = "reviewed_by_employee_id")
//    private Employee reviewedBy;
//
//   @Column(nullable = false)
//    private boolean overridden = false;
}
