package com.student.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.student.entity.Student;
import com.student.repository.StudentRepository;

@DataJpaTest
public class RepositoryTest {
	
	@Autowired
	StudentRepository repository;
	
	
	@Test
	void shouldFindStudentByFirstName() {
		Student student = new Student(1L, "Jhon", "Jose", "jhon@gmail.com", 20);
		//when
		repository.save(student);
		
		List<Student> response = repository.findAllByFirstNameContaining(student.getFirstName());

		//then
		assertThat(response).isNotNull().isNotEmpty();
	}

}
