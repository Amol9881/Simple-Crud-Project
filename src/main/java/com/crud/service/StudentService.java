package com.crud.service;

import java.util.List;

import com.crud.model.Student;

public interface StudentService {
	
	public void save(Student s);//Post
	
	public List<Student> getAllData();//GetAll
	
	public Student getById(int rollno);//GetSpecific Using Id
	
	public void updateAllData(Student s);//Put Update All Data
	
	public Student updateSpecificData(int rollno,Student s);//Patch Use to Change Specific Data 
	
	public void deletespecificdata(int rollno);//Delete
	
	public void deleteAll();
	
   
    
    public List<Student> pagingingandsorting(int pageno,int pagesize,String sortBy);
    
    public void updateName(int rollno,String name);

	
	
	
	
	
	
	



}
