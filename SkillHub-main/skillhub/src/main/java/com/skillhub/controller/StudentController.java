package com.skillhub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.skillhub.entity.Student;
import com.skillhub.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Register Student
    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@Valid @RequestBody Student student) {

        Student savedStudent = studentService.registerStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<Optional<Student>> login(@RequestBody Student student) {

        Optional<Student> loggedInStudent =
                studentService.login(student.getEmail(), student.getPassword());

        return ResponseEntity.ok(loggedInStudent);
    }

    // Get All Students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);

        return ResponseEntity.ok(student);
    }

    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student student) {

        Student updatedStudent = studentService.updateStudent(id, student);

        return ResponseEntity.ok(updatedStudent);
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok("Student deleted successfully.");
    }
}