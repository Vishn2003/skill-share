package com.skillhub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skillhub.exception.ResourceNotFoundException;
import com.skillhub.entity.Student;
import com.skillhub.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Register Student
    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get Student by ID
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Student not found with ID: " + id));
    }

    // Update Student
    public Student updateStudent(Long id, Student updatedStudent) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        // Keep the existing password when the profile form does not submit a new one.
        if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isBlank()) {
            student.setPassword(updatedStudent.getPassword());
        }
        student.setDepartment(updatedStudent.getDepartment());
        student.setYear(updatedStudent.getYear());

        return studentRepository.save(student);
    }

    // Delete Student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Login
    public Optional<Student> login(String email, String password) {

        Optional<Student> student = studentRepository.findByEmail(email);

        if(student.isPresent() &&
           student.get().getPassword().equals(password)){

            return student;
        }

        return Optional.empty();
    }

}