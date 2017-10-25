package com.sisound.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="welcome")
public class WelcomeController {

	@RequestMapping(value="hello", method=RequestMethod.GET)
	public String welcome(HttpSession session){
		if(session.isNew()){
			return "index";
		}
		return "main";
	}
}
