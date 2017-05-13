package com.luojiash.spring.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luojiash.test.dto.Student;
import org.springframework.web.util.CookieGenerator;

@Controller
public class SpController {
    
    @RequestMapping("cookie/generate")
    public @ResponseBody
    Object cookieGenerator(HttpServletRequest request, HttpServletResponse response,
                           String key, String val) {
        if (StringUtils.isAnyBlank(key, val)) {
            return "key/val is required";
        }
        CookieGenerator cg = new CookieGenerator();
        cg.setCookieDomain(request.getServerName());
        cg.setCookieName(key);
        cg.setCookiePath("/");
        cg.addCookie(response, val);
        return "cookie set: " + key + "=" + val;
    }

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
