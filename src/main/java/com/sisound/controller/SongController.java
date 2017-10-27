package com.sisound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SongController {

	//uploading a song
	@RequestMapping(value="uploadPage", method=RequestMethod.GET)
	public String uploadPage(){
		return "upload";
	}
}
