package com.EducationSystem.Enrollement.Services;

import com.EducationSystem.Enrollement.Model.Course;
import com.EducationSystem.Enrollement.Repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepo courseRepository;

    @Autowired
    public CourseService(CourseRepo courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Integer courseCode) {
        return courseRepository.findById(courseCode);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public String deleteCourse(Integer courseCode) {
        courseRepository.deleteById(courseCode);
        return "Successfully deleted course ID: " + courseCode;
    }
}
