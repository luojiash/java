package com.luojiash.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AspectTestService aspectTestService;

    @Test
    public void test() {
        try {
            studentService.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t() {
        aspectTestService.t();
    }
}
