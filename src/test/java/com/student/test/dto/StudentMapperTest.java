package com.student.test.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.tool.schema.internal.StandardUniqueKeyExporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.dto.StudentDto;
import com.student.dto.StudentMapper;
import com.student.dto.StudentResponseDto;
import com.student.entity.Student;

@ExtendWith(MockitoExtension.class)
public class StudentMapperTest {
	
	
	private StudentMapper mapper;
	

	@BeforeEach
	void setUp() {
		mapper = new StudentMapper();
	}
	
	@Test
	void shouldMapStudentDtoToStudent() {
		StudentDto dto = new StudentDto("Jose","carlos", "jose@gmail.com", 1);
		Student student = mapper.toStudent(dto);
		
		assertEquals(dto.getFirstName(), student.getFirstName());
		assertEquals(dto.getLastName(), student.getLastName());
		assertEquals(dto.getEmail(), student.getEmail());
//		assertNotNull(student.getSchool());
//		assertEquals(dto.getIdSchool(), student.getSchool().getId());
		
	}
	@Test
	void shouldMapStudentToStudentResponseDto() {
		//given
		Student student = new Student(null, "Jose", "Marcao", "marcao@gmail.com", 11);
		//when
		StudentResponseDto response = mapper.toStudentResponseDto(student);
		//then
		assertEquals(response.getFirstName(), student.getFirstName());
		assertEquals(response.getLastName(), student.getLastName());
		assertEquals(response.getEmail(), student.getEmail());
		
	}
	@Test
	void shouldReturnExceptionWhenDtoIsNull() {

		var exp = assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
		 assertEquals("The student Dto is null", exp.getMessage());
		
		
	}

}
