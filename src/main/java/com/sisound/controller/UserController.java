package com.sisound.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sisound.model.User;
import com.sisound.model.db.UserDao;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;
	
	
	//register
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String addDiuner(Model m){
		User u = new User();
		m.addAttribute("user", u);
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String saveDiuner(@ModelAttribute User newUser){
		try {
			userDao.insertUser(newUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "main";
	}

}
