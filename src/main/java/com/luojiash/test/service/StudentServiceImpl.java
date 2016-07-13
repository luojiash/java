package com.luojiash.test.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luojiash.test.dao.StudentDAO;
import com.luojiash.test.dto.Student;

/**
 * 接口和实现类都有@Transactional注解，实现类注解优先。
 * 默认情况下，方法抛出unchecked异常才会回滚，checked异常不回滚，rollbackFor指定回滚的异常类型。
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDAO studentDAO;
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public void test() throws IOException {
        Student student = new Student();
        student.setName("jjj");
        student.setAge(33);
        
        studentDAO.insertStudent(student);
//        new BigDecimal("sss");
        new FileInputStream("c:/abc.pl").close();
        studentDAO.insertStudent(student);
    }
}
