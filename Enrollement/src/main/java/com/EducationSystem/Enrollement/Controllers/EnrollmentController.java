package com.EducationSystem.Enrollement.Controllers;

import com.EducationSystem.Enrollement.Model.Course;
import com.EducationSystem.Enrollement.Model.Enrollment;
import com.EducationSystem.Enrollement.Model.Student;
import com.EducationSystem.Enrollement.Repository.CourseRepository;
import com.EducationSystem.Enrollement.Repository.StudentRepository;
import com.EducationSystem.Enrollement.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @PostMapping
    public Enrollment createEnrollment(@RequestBody Map<String, Object> enrollmentData) {
        Long studentId = Long.valueOf(enrollmentData.get("studentId").toString());
        String courseCode = enrollmentData.get("courseCode").toString();
        LocalDate enrollmentDate = LocalDate.parse(enrollmentData.get("enrollmentDate").toString());
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        System.out.println("Fetched Student: " + student);
        System.out.println("Fetched Course: " + course);
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(enrollmentDate);
        return enrollmentService.saveEnrollment(enrollment);
    }
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}