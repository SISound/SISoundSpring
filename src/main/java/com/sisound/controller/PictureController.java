package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="getPicProfile", method=RequestMethod.GET)
	public void getProfilePic(HttpServletResponse resp, HttpSession session){

		try {
			File file=new File((String)session.getAttribute("avatar"));
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@RequestMapping(value="getPicCover", method=RequestMethod.GET)
//	public void getCoverPic(HttpServletResponse resp, HttpSession session){
//
//		try {
//			File file=new File((String)session.getAttribute("cover"));
//			Files.copy(file.toPath(), resp.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
