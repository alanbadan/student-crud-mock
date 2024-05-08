package com.student.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.student.dto.StudentDto;
import com.student.dto.StudentMapper;
import com.student.dto.StudentResponseDto;
import com.student.repository.StudentRepository;

@Service
public class StudentService {
	
	private final StudentRepository repository;
	private final StudentMapper mapper;
	
	public StudentService(StudentRepository repository, StudentMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public StudentResponseDto saveStudent(StudentDto dto) {
		
		var student = mapper.toStudent(dto);
		var studentSave = repository.save(student);
		return mapper.toStudentResponseDto(studentSave);
	}
	
	public List<StudentResponseDto> findAll(){
		return repository.findAll().stream().map(mapper::toStudentResponseDto).collect(Collectors.toList());
	}
	
	public StudentResponseDto findStudentById(Long id) {
		return repository.findById(id).map(mapper::toStudentResponseDto).orElse(null);
	}
	
	public List<StudentResponseDto> findStudentByName(String firstName){
		return repository.findAllByFirstNameContaining(firstName)
				          .stream()
				          .map(mapper::toStudentResponseDto)
				          .collect(Collectors.toList());
	}
	
	public void deleteStudentbyId(Long id) {
		repository.deleteById(id);
		
	}
}
