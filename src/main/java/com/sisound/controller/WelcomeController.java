package com.sisound.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sisound.model.User;


@Controller
public class WelcomeController {

	@RequestMapping(value="/index")
	public String welcome(HttpSession session){
		Object o = session.getAttribute("logged");
//		User u = (User)session.getAttribute("user");
//		System.out.println(u.getUsername());
//		System.out.println(u.getUsername());
//		System.out.println(u.getUsername());
//		System.out.println(u.getUsername());
//		System.out.println(u.getUsername());
		boolean logged =  (o != null && ((boolean) o ));
		if(session.isNew() || !logged){
			return "index";
		}
		else{
			return "main";
		}
	}
}
