package com.luojiash.test.service;

import org.springframework.stereotype.Service;

import com.luojiash.test.annotation.LogAn;

@Service
public class AspectTestService {

    @LogAn
    public String t() {
        System.out.println("t ing");
        return "t";
    }
}
