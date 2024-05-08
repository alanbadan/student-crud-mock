package com.student.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import com.student.dto.StudentDto;
import com.student.dto.StudentMapper;
import com.student.dto.StudentResponseDto;
import com.student.entity.Student;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
	
	@InjectMocks
	StudentService studentService;
	
	@Mock
	StudentRepository repository;
	
	@Mock
	StudentMapper mapper;
	
	
	@Test
	void shouldReturnStudentSave() {
		//given
		StudentDto dto = new StudentDto("Jhon","Joe", "ghon@gmail.com", 11);
		Student student = new Student(null, "Jhon","Joe", "ghon@gmail.com", 11);
		Student studentSaved = new Student(null, "Jhon","Joe", "ghon@gmail.com", 11);
		studentSaved.setId(1L);
		
		when(mapper.toStudent(dto)).thenReturn(student);
		when(repository.save(student)).thenReturn(studentSaved);
		when(mapper.toStudentResponseDto(studentSaved)).thenReturn(new StudentResponseDto("Jhon","Joe", "ghon@gmail.com"));
		
		//when
		StudentResponseDto response = studentService.saveStudent(dto);
		//then
		assertEquals(dto.getFirstName(), response.getFirstName());
		assertEquals(dto.getLastName(), response.getLastName());
		assertEquals(dto.getEmail(), response.getEmail());
		
		verify(mapper, times(1)).toStudent(dto);
		verify(repository, times(1)).save(student);
		verify(mapper, times(1)).toStudentResponseDto(studentSaved);
	}
	
	@Test
	 void ShouldReturnAllStudent() {
		//given
		List<Student> listStudent = new ArrayList<>();
		listStudent.add(new Student(null, "Jhon","Joe", "ghon@gmail.com", 11));
		//when
		when(repository.findAll()).thenReturn(listStudent);
		when(mapper.toStudentResponseDto(any(Student.class)))
		            .thenReturn(new StudentResponseDto("Jhon","Joe", "ghon@gmail.com"));
		
		List<StudentResponseDto> responseList = studentService.findAll();
		//then
		assertEquals(listStudent.size(), responseList.size());
		
	}
	
	@Test
	void ShouldReturnStudentById() {
		//given
		Long id = 1L;
		Student student =  new Student(null, "Jhon","Joe", "ghon@gmail.com", 11); 
		
		//when
		when(repository.findById(id)).thenReturn(Optional.of(student));
		when(mapper.toStudentResponseDto(any(Student.class)))
        .thenReturn(new StudentResponseDto("Jhon","Joe", "ghon@gmail.com"));
        
		StudentResponseDto dto = studentService.findStudentById(id);
		
		//then
		assertEquals(dto.getFirstName(),student.getFirstName());
		assertEquals(dto.getLastName(), student.getLastName());
		assertEquals(dto.getEmail() ,student.getEmail());
		
		verify(repository,times(1)).findById(id);
		
	}
	
	@Test
	void shouldReturnStudentByName() {
		String studentName = "Jhon";
		//given
	    List<Student> listStudent = new ArrayList<>();
	    listStudent.add(new Student(null, "Jhon","Joe", "ghon@gmail.com", 11));
		//when
		when(repository.findAllByFirstNameContaining(studentName)).thenReturn(listStudent);
		when(mapper.toStudentResponseDto(any(Student.class)))
				            .thenReturn(new StudentResponseDto("Jhon","Joe", "ghon@gmail.com"));
		
		var responseDto = studentService.findStudentByName(studentName);
		//then
		assertEquals(listStudent.size(), responseDto.size());
		verify(repository, times(1)).findAllByFirstNameContaining(studentName);
		
			
	}
	@Test
	@DirtiesContext //pay Attention
	void shouldDeleteStudentById() {
		
		//given
		Long id = 1L;
		//when
		repository.deleteById(id);		
		Optional<Student> response = repository.findById(id);
		//then
		assertFalse(response.isPresent());
		verify(repository, times(1)).deleteById(id);
	
		
		
	}

}
