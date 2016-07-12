package com.luojiash.test.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luojiash.test.dao.StudentDAO;
import com.luojiash.test.dto.Student;

@Service
public class StudentService {
    @Autowired
    private StudentDAO studentDAO;
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public void test() throws FileNotFoundException {
        Student student = new Student();
        student.setName("jjj");
        student.setAge(33);
        
        studentDAO.insertStudent(student);
//        new BigDecimal("sss");
        new FileInputStream("c:/abc.pl");
        studentDAO.insertStudent(student);
    }
}
