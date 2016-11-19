package com.luojiash.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.luojiash.util.HttpUtils;

@Controller
public class FileUploadController {

	@RequestMapping("page")
	public ModelAndView page(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("page");
	}
	
	@RequestMapping("qiniu/page")
    public ModelAndView qiniuPage(HttpServletRequest request,HttpServletResponse response) {
        return new ModelAndView("qiniu");
    }
	
	private final String IMG_PATH="c:\\file\\";
	
	/*@RequestMapping("file/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,HttpServletResponse response) {
	    DiskFileItemFactory factory=new DiskFileItemFactory();
	    ServletFileUpload sfu=new ServletFileUpload(factory);
	    try {
            List<FileItem> items=sfu.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    //保存图片
                    String fileName=item.getName();
                    File file=new File(IMG_PATH+fileName);
                    item.write(file);
                    return file.getPath();
                    //直接返回图片，不保存
                    InputStream inputStream=item.getInputStream();
                    OutputStream outputStream=response.getOutputStream();
                    byte []temp=new byte[1024];
                    int n=-1;
                    while ((n=inputStream.read(temp))!=-1) {
                        outputStream.write(temp, 0, n);
                    }
                }
            }
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return "";
	}*/

    @RequestMapping(value = "file/upload", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String upload(@RequestParam("upload") MultipartFile file) {
        try {
            file.transferTo(new File(IMG_PATH + file.getOriginalFilename()));
            return file.getOriginalFilename();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

	@RequestMapping("file")
    @ResponseBody
    public void upload(HttpServletRequest request,HttpServletResponse response,String path) {
        File file=new File(IMG_PATH+path);
        try {
            FileInputStream fis=new FileInputStream(file);
            OutputStream outputStream=response.getOutputStream();
            
            byte []temp=new byte[1024];
            int n=-1;
            while ((n=fis.read(temp))!=-1) {
                outputStream.write(temp, 0, n);
            }
            fis.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
	
}
