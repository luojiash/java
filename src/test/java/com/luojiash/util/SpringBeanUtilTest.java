package com.luojiash.util;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.luojiash.test.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringBeanUtilTest {

    @Test
    public void test() {
        List<StudentService> list = SpringBeanUtil.getBeans(StudentService.class);
        for (StudentService studentService : list) {
            System.out.println(studentService.getClass().getName());
        }
    }

}
