package com.EducationSystem.Enrollement.Services;

import com.EducationSystem.Enrollement.Model.Course;
import com.EducationSystem.Enrollement.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Save the course to the database
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by course code
    public Optional<Course> getCourseByCode(String courseCode) {
        return courseRepository.findById(courseCode);
    }

    // Update course by course code
    public Course updateCourse(String courseCode, Course courseDetails) {
        Optional<Course> existingCourseOpt = courseRepository.findById(courseCode);
        if (existingCourseOpt.isPresent()) {
            Course existingCourse = existingCourseOpt.get();
            existingCourse.setCourseTitle(courseDetails.getCourseTitle());
            existingCourse.setCategory(courseDetails.getCategory());
            // If you want to update enrollments, you can add the logic here
            return courseRepository.save(existingCourse);
        }
        return null; // Handle error in the controller if the course doesn't exist
    }

    // Delete a course by course code
    public void deleteCourse(String courseCode) {
        courseRepository.deleteById(courseCode);
    }
}
