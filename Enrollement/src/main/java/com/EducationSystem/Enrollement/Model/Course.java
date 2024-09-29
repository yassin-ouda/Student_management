package com.EducationSystem.Enrollement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course {

    @Id
    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @Column(name = "category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    // Getters and Setters
}
