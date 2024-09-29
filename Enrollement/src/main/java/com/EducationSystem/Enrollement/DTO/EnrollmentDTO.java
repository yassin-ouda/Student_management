package com.EducationSystem.Enrollement.DTO;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentDTO {
    private Long enrollmentId;
    private String courseCode; // or another identifier
    private LocalDate enrollmentDate;

    // This DTO can reference a CourseDTO if needed
}
