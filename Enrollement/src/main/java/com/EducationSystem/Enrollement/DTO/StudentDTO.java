package com.EducationSystem.Enrollement.DTO;


import lombok.Data;

@Data
public class StudentDTO {
    private Long studentId;
    private String studentName;
    private String studentPhone;

    // You can add additional fields if needed, such as enrollments (with minimal data).
}
