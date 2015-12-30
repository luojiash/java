package com.luojiash.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luojiash.util.HttpUtils;

@Controller
public class TestController {

	@RequestMapping("abc")
	public @ResponseBody String ip(HttpServletRequest request,HttpServletResponse response) {
		return HttpUtils.getIp(request);
	}
	
	@RequestMapping("param")
	public @ResponseBody String param(HttpServletRequest request,HttpServletResponse response,@RequestBody String data) {
		try {
			System.out.println(data);
			return HttpUtils.getPostData(request);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
