package com.EducationSystem.Enrollement.Controllers;

import com.EducationSystem.Enrollement.Model.Course;
import com.EducationSystem.Enrollement.Model.Enrollment;
import com.EducationSystem.Enrollement.Model.Student;
import com.EducationSystem.Enrollement.Services.CourseService;
import com.EducationSystem.Enrollement.Services.EnrollmentService;
import com.EducationSystem.Enrollement.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.EducationSystem.Enrollement.DTO.StudentDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/savestudent")
    public ResponseEntity<?> createStudentWithCourse(@RequestBody Map<String, Object> studentData, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError() != null ? result.getFieldError().getDefaultMessage() : "Validation error";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            // Extract student data
            String studentName = studentData.get("studentName").toString();
            String studentPhone = studentData.get("studentPhone").toString();

            // Create and save student
            Student student = new Student();
            student.setStudentName(studentName);
            student.setStudentPhone(studentPhone);
            Student savedStudent = studentService.saveStudent(student);

            // Extract course data
            Map<String, Object> courseData = (Map<String, Object>) studentData.get("course");
            String courseCode = courseData.get("courseCode").toString();
            String courseTitle = courseData.get("courseTitle").toString();
            String category = courseData.get("category").toString();

            // Check if course already exists, otherwise create a new one
            Optional<Course> courseOptional = courseService.getCourseByCode(courseCode);
            Course course;

            if (courseOptional.isPresent()) {
                course = courseOptional.get(); // Course already exists
            } else {
                course = new Course();
                course.setCourseCode(courseCode);
                course.setCourseTitle(courseTitle);
                course.setCategory(category);
                courseService.saveCourse(course);
            }

            // Create enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(savedStudent);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());  // Auto-generate current date for enrollment
            enrollmentService.saveEnrollment(enrollment);

            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the student or course");
        }
    }
    @PostMapping("/updatestudent/{studentId}")
    public ResponseEntity<?> updateStudentWithCourse(@PathVariable Long studentId, @RequestBody Map<String, Object> studentData, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError() != null ? result.getFieldError().getDefaultMessage() : "Validation error";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            // Fetch the existing student
            Student existingStudent = studentService.getStudentById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            // Update student fields
            String studentName = studentData.get("studentName").toString();
            String studentPhone = studentData.get("studentPhone").toString();
            existingStudent.setStudentName(studentName);
            existingStudent.setStudentPhone(studentPhone);

            // Save updated student
            Student updatedStudent = studentService.updateStudent(existingStudent);

            // Extract course data
            Map<String, Object> courseData = (Map<String, Object>) studentData.get("course");
            String courseCode = courseData.get("courseCode").toString();
            String courseTitle = courseData.get("courseTitle").toString();
            String category = courseData.get("category").toString();

            // Check if course already exists, otherwise create a new one
            Course course = courseService.getCourseByCode(courseCode)
                    .orElseGet(() -> {
                        Course newCourse = new Course();
                        newCourse.setCourseCode(courseCode);
                        newCourse.setCourseTitle(courseTitle);
                        newCourse.setCategory(category);
                        return courseService.saveCourse(newCourse);
                    });

            // Create or update enrollment
            Enrollment enrollment = enrollmentService.getAllEnrollments().stream()
                    .filter(e -> e.getStudent().getStudentId().equals(updatedStudent.getStudentId()) && e.getCourse().getCourseCode().equals(course.getCourseCode()))
                    .findFirst()
                    .orElse(new Enrollment());

            enrollment.setStudent(updatedStudent);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());  // Auto-generate current date for enrollment
            enrollmentService.saveEnrollment(enrollment);

            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the student or course");
        }
    }


    // Get all students with their courses
    @GetMapping("/liststudents")
    public ResponseEntity<List<StudentWithCourses>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        List<StudentWithCourses> studentsWithCourses = students.stream()
                .map(student -> new StudentWithCourses(student.getStudentId(), student.getStudentName(), student.getStudentPhone(),
                        student.getEnrollments().stream()
                                .map(enrollment -> enrollment.getCourse())
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentsWithCourses);
    }

    // Get student by ID along with their courses
    @GetMapping("/getstudent/{studentId}")
    public ResponseEntity<StudentWithCourses> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            StudentWithCourses studentWithCourses = new StudentWithCourses(student.get().getStudentId(), student.get().getStudentName(),
                    student.get().getStudentPhone(), student.get().getEnrollments().stream()
                    .map(enrollment -> enrollment.getCourse())
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(studentWithCourses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Inner class to return student with courses
    private static class StudentWithCourses {
        private Long studentId;
        private String studentName;
        private String studentPhone;
        private List<Course> courses;

        public StudentWithCourses(Long studentId, String studentName, String studentPhone, List<Course> courses) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.studentPhone = studentPhone;
            this.courses = courses;
        }

        // Getters and Setters
        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentPhone() {
            return studentPhone;
        }

        public void setStudentPhone(String studentPhone) {
            this.studentPhone = studentPhone;
        }

        public List<Course> getCourses() {
            return courses;
        }

        public void setCourses(List<Course> courses) {
            this.courses = courses;
        }
    }
    @DeleteMapping("/deletestudent/{id}")
    public String deleteStudent( @PathVariable Long id ) {
        studentService.deleteStudent(id);
        return "sucessfully deleted studentwith id:"+String.valueOf(id);
    }
}
