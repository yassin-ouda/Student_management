package com.EducationSystem.Enrollement.Services;

import com.EducationSystem.Enrollement.Model.Student;
import com.EducationSystem.Enrollement.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepo studentRepository;

    @Autowired
    public StudentService(StudentRepo studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public String deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
        return "Successfully deleted studentId: " + studentId;
    }
}
