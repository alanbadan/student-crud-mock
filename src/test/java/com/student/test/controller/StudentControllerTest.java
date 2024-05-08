package com.student.test.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.controller.StudentController;
import com.student.dto.StudentDto;
import com.student.dto.StudentResponseDto;
import com.student.entity.Student;
import com.student.service.StudentService;

@WebMvcTest
public class StudentControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	StudentController studentController;
	
	@MockBean
	StudentService studentService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	private static final String FirstName = "Jhon";
	private static final String LastName = "Joe";
	private static final String Email = "jhon@gmail.com";
/*	
	@BeforeEach
	void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(studentController).alwaysDo(print()).build();
	}
	*/
	
	@Test
	void shouldSaveStudent() throws JsonProcessingException, Exception {
		
		StudentDto dto = new StudentDto("Jhon","Joe", "jhon@gmail.com", 11);
		StudentResponseDto studentResponse = new StudentResponseDto(FirstName, LastName, Email);
		
		when(studentService.saveStudent(dto)).thenReturn(studentResponse);
		
		
		ResultActions response = mockMvc.perform(post("/student")
		       .contentType(MediaType.APPLICATION_JSON)
		       .content(objectMapper.writeValueAsString(studentResponse)));
				
		//then		
		
		    response.andDo(print()) 
		            .andExpect(status().isCreated())
		            .andExpect(jsonPath("$.FirstName", is(studentResponse.getFirstName())))
		            .andExpect(jsonPath("$.LastName", is(studentResponse.getLastName())))
		            .andExpect(jsonPath("$.Email", is(studentResponse.getEmail())));
		
	}
	
	@Test
	void shouldReturnAllStudents() throws Exception {
		StudentResponseDto student1 = new StudentResponseDto("Jhon","Joe", "jhon@gmail.com");
		StudentResponseDto student2 = new StudentResponseDto("Maria","Jose", "maria@gmail.com");
		
		List<StudentResponseDto> listStudent =  new ArrayList<>();
		listStudent.add(student1);
		listStudent.add(student2);
		
		when(studentService.findAll()).thenReturn(listStudent);
		
		ResultActions response = mockMvc.perform(get("/student"));
		
		//then
		response.andDo(print())
		         .andExpect(status().isOk())
		         .andExpect(jsonPath("$.size()", is(listStudent.size())));
				      		
	} 
	@Test 
	void shouldReturnStudentById() throws Exception {
		Long id = 1L;
		StudentResponseDto student = new StudentResponseDto( "Jhon","Joe", "jhon@gmail.com");
		
		when(studentService.findStudentById(id)).thenReturn(student);
		
		ResultActions response = mockMvc.perform(get("/student/{id}", id));
		
		//then
		response.andDo(print())
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
		        .andExpect(jsonPath("$.lastName", is(student.getLastName())))
		        .andExpect(jsonPath("$.email", is(student.getEmail())));
	}
    
	@Test
	void shouldReturnStudentByName() throws Exception {
		String firstName = "Jhon";
		StudentResponseDto student1 = new StudentResponseDto( "Jhon","Joe", "jhon@gmail.com");
		StudentResponseDto student2 = new StudentResponseDto( "Jhon","Marcos", "jhon@gmail.com");
		
		List<StudentResponseDto> listStudent =  new ArrayList<>();
		listStudent.add(student1);
		listStudent.add(student2);
		
		when(studentService.findStudentByName(firstName)).thenReturn(listStudent);
		
		ResultActions response = mockMvc.perform(get("/student/search/{firstName}", firstName));
		//then
		response .andDo(print())
		         .andExpect(status().isOk())
		         .andExpect(jsonPath("$.size()", is(listStudent.size())));
	}
	
	@Test
	void shouldDeleteStudentById() throws Exception {
		
		Long id = 1L;	
// student = new Student( 1L,"Jhon","Joe", "jhon@gmail.com",11);
		doNothing().when(studentService).deleteStudentbyId(id);
		
		ResultActions response =  mockMvc.perform(delete("/student/{id}", id));
		response .andDo(print())
		         .andExpect(status().isOk());
		         
		         
	}

}
