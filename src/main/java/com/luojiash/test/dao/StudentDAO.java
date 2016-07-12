package com.luojiash.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luojiash.test.dto.Student;

@Repository
public interface StudentDAO {
    
    @Insert("insert into student (name,age,createTime) values(#{name},#{age},now())")
    public int insertStudent(Student student);
}
