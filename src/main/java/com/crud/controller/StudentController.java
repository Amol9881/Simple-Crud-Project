package com.crud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.model.Student;
import com.crud.service.StudentService;

@RestController
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping("/save")
	public void saveData(@RequestBody Student s) {
		studentService.save(s);
	}

	@GetMapping("/getalldata")
	public List<Student> getAllData() {
		return studentService.getAllData();
	}

	@GetMapping("/findbyid/{rollno}")
	public Student findById(@PathVariable int rollno) {
		return studentService.getById(rollno);
	}

	@PutMapping("/updateall")
	public void updateAllData(@RequestBody Student s) {
		studentService.save(s);
	}

	@PatchMapping("/updatespecific/{rollno}")
	public Student updateSpecificData(@PathVariable int rollno, @RequestBody Student s) {
		return studentService.updateSpecificData(rollno, s);
	}

	@DeleteMapping("/deletespecific/{rollno}")
	public void deleteById(@PathVariable int rollno) {
		studentService.deletespecificdata(rollno);
	}

	@DeleteMapping("/deleteall")
	public void deleteAll() {
		studentService.deleteAll();
	}

	@GetMapping("/pagingAndSorting/{pageno}/{pagesize}/{sortBy}")
	public List<Student> pagingAndSorting(@PathVariable int pageno, @PathVariable int pagesize,
			@PathVariable String sortBy) {
		return studentService.pagingingandsorting(pageno, pagesize, sortBy);
	}

	@PatchMapping("/updateName/{rollno}")
	public String updateName(@PathVariable int rollno, @RequestBody Student s) {
		studentService.updateName(rollno, s.getName());
		return "Name updated___!" + rollno;
	}
}