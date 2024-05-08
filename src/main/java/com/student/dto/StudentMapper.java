package com.student.dto;

import org.springframework.stereotype.Service;

import com.student.entity.Student;

@Service
public class StudentMapper {
	
	
	public Student toStudent(StudentDto dto) {
		
		if(dto == null) {
			throw new NullPointerException("The student Dto is null");
		}
		
		var student = new Student();
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setAge(dto.getAge());
//		var school = new School();
//		school.setId(dto.getIdSchool());
//		student.setSchool(school);
		return student;
	}
	
	public StudentResponseDto toStudentResponseDto(Student student) {
		
		 var studentResponse = new StudentResponseDto();
		 studentResponse.setFirstName(student.getFirstName());
		 studentResponse.setLastName(student.getLastName());
		 studentResponse.setEmail(student.getEmail());
		 return studentResponse;
		 
		 }
	

}
