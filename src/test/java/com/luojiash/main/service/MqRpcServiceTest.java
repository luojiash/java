package com.luojiash.main.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class MqRpcServiceTest {

    @Autowired
    private MqRpcService mqRpcService;

    @Test
    public void testSend() {
        String resp = mqRpcService.send("Hello!");
        System.out.println(resp);
    }

}
