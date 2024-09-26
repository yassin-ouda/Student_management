package com.EducationSystem.Enrollement.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="course")
public class Course {
    @Id
    private Integer courseCode;
    private String courseTitle;
    private String category;
}
