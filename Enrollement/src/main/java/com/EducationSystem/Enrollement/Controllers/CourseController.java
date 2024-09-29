package com.EducationSystem.Enrollement.Controllers;

import com.EducationSystem.Enrollement.Model.Course;
import com.EducationSystem.Enrollement.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    // Create a new course
    @PostMapping("/savecourse")
    public Course createCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }
    // Get all courses
    @GetMapping("/listcourses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    // Get a course by code
    @GetMapping("/getcourse/{courseCode}")
    public ResponseEntity<Course> getCourseByCode(@PathVariable String courseCode) {
        Optional<Course> course = courseService.getCourseByCode(courseCode);
        return course.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    // Update a course by code
    @PutMapping("updatecourse/{courseCode}")
    public Course updateCourse(@PathVariable String courseCode, @RequestBody Course courseDetails) {
        return courseService.updateCourse(courseCode, courseDetails);
    }
    // Delete a course by code
    @DeleteMapping("delete/{courseCode}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseCode) {
        courseService.deleteCourse(courseCode);
        return ResponseEntity.noContent().build();
    }
}