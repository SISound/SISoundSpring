package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisound.model.User;
import com.sisound.model.db.UserDao;

@Controller
@MultipartConfig
public class PictureController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="getPicHeader", method=RequestMethod.GET)
	@ResponseBody
	public void getHeaderPic(HttpServletResponse resp, HttpSession session){
		User u = (User)session.getAttribute("sessionUser");
		File file=new File(u.getProfilPicture());
		try {
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			resp.setStatus(503);
		}
	}
	
	
	@RequestMapping(value="getPic{username}", method=RequestMethod.GET)
	@ResponseBody
	public void getPic(@PathVariable String username, HttpServletResponse resp){
		try {
			User u=userDao.getUser(username);
	
			File file=new File(u.getProfilPicture());
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException | SQLException e) {
			resp.setStatus(503);
		} 
	}
	
	@RequestMapping(value="getCover{username}", method=RequestMethod.GET)
	@ResponseBody
	public void getCover(@PathVariable String username, HttpServletResponse resp){
		try {
			User u=userDao.getUser(username);
			System.out.println(u.getCoverPhoto());
			System.out.println(u.getCoverPhoto());
			File file=new File(u.getCoverPhoto());
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException | SQLException e) {
			resp.setStatus(503);
		} 
	}
}
	