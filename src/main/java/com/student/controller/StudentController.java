package com.student.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.dto.StudentDto;
import com.student.dto.StudentResponseDto;
import com.student.service.StudentService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	
	private final StudentService studentService;
	
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	
	@PostMapping
	public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentDto dto){
		StudentResponseDto response = studentService.saveStudent(dto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<List<StudentResponseDto>> getAllStudents(){
		return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentResponseDto> getById(@PathVariable("id") Long id){
		   StudentResponseDto response =  studentService.findStudentById(id);
		   return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/search/{firstName}")
	public ResponseEntity<List<StudentResponseDto>> findByName(@PathVariable("firstName") String firstName){
		return new ResponseEntity<>(studentService.findStudentByName(firstName),HttpStatus.OK);
	}
   
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable ("id") Long id){
		studentService.deleteStudentbyId(id);
		return new ResponseEntity<String>("Student deleted with successfull",HttpStatus.OK);
	}
}
