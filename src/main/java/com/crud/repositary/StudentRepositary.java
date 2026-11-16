package com.crud.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.crud.model.Student;

public interface StudentRepositary extends JpaRepository<Student, Integer> {


@Modifying
@Transactional
@Query("update Student set name=:name where rollno=:rollno")
public void updateName(int rollno,String name);

}
