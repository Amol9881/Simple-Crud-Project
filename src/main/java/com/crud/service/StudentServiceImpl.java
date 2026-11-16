package com.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.crud.exception.ResourceNotFoundException;
import com.crud.model.Student;
import com.crud.repositary.StudentRepositary;

@Service
public class StudentServiceImpl implements StudentService {

	StudentRepositary sr;
	private Optional<Student> optional;

	public StudentServiceImpl(StudentRepositary sr) {
		this.sr = sr;
	}

	@Override
	public void save(Student s) {
		sr.save(s);

	}

	@Override
	public List<Student> getAllData() {
		List<Student> stu = (List<Student>) sr.findAll();
		return stu;
	}

	@Override
	public Student getById(int rollno) {
//		Student s = sr.findById(rollno).get();
		  Optional<Student> stu = sr.findById(rollno);
		  if(stu.isPresent())
		  {
			  return stu.get();
		  }
		  else
		  {
			 throw new ResourceNotFoundException("Resourcenotfound.....!"+rollno);
		  }
      
	}

	@Override
	public void updateAllData(Student s) {
		sr.save(s);

	}

	@Override
	public Student updateSpecificData(int rollno, Student s) {
		Student stu = sr.findById(rollno).get();
		if (s.getName() != null) {
			stu.setName(s.getName());
		}
		if (s.getEmail() != null) {
			stu.setEmail(s.getEmail());
		}
		if (s.getContact() != null) {
			stu.setContact(s.getContact());
		}
		return sr.save(stu);
	}

	@Override
	public void deletespecificdata(int rollno) {
		sr.deleteById(rollno);
	}

	@Override
	public void deleteAll() {
		sr.deleteAll();

	}



	@Override
	public List<Student> pagingingandsorting(int pageno, int pagesize, String sortBy) {
		Pageable pageable = PageRequest.of(pageno, pagesize, Sort.by(sortBy));
		List<Student> list = sr.findAll(pageable).getContent();
		return list;
	}

	@Override
	public void updateName(int rollno, String name) {

		sr.updateName(rollno, name);

	}



}
