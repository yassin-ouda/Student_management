package com.EducationSystem.Enrollement.Services;

import com.EducationSystem.Enrollement.Model.Student;
import com.EducationSystem.Enrollement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Save a new student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get a student by ID
    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    // Update an existing student
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    // Delete a student by ID
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
