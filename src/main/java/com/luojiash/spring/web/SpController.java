package com.luojiash.spring.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luojiash.test.dto.Student;

@Controller
public class SpController {
    
    @RequestMapping("v")
    public @ResponseBody Object v(@Valid Student student, BindingResult result,HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            printErr(result.getAllErrors());
        }
        return student;
    }
    
    private void printErr(List<ObjectError> errors) {
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                System.out.println(((FieldError) error).getField() + " "
                        + error.getDefaultMessage());
            } else {
                System.out.println(error);
            }
        }
    }
}
