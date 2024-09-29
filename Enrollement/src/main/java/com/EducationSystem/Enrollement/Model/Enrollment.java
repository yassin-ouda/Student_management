package com.EducationSystem.Enrollement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_code", nullable = false)
    @JsonBackReference
    private Course course;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @PrePersist
    public void prePersist() {
        this.enrollmentDate = LocalDate.now();  // Automatically set the current date
    }

    // Getters and Setters
}
