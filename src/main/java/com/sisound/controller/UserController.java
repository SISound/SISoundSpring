package com.sisound.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.GenresDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	UserDao userDao;
	@Autowired
	SongDao songDao;
	@Autowired
	GenresDao genresDao;
	
	//REGISTERING USER
	@RequestMapping(value="regPage", method = RequestMethod.GET)
	public String addUser(Model m){
		User u = new User();
		m.addAttribute("user", u);
		return "register";
	}
	
	@RequestMapping(value="registerUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute User u, HttpServletRequest request){
		
		String password2 = request.getParameter("password2");
		
		System.out.println(u.getPassword());
		System.out.println(password2);
		if(!u.getPassword().equals(password2)){
			request.setAttribute("error", "passwords missmatch");
			return "regPage";
		}
		
		try {
			if(!userDao.usernameExists(u.getUsername()) && !userDao.emailExists(u.getEmail())){
				userDao.insertUser(u);
				request.getSession().setAttribute("user", u);
				request.getSession().setAttribute("logged", true);
				return "main";
			} 
			else if(userDao.usernameExists(u.getUsername())){
				request.setAttribute("error", "username is taken");
				return "regPage";
			} 
			else {
				request.setAttribute("error", "e-mail already in use");
				return "regPage";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "index";
		}		
	}

	//LOGIN USER
	@RequestMapping(value="loginPage", method=RequestMethod.GET)
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping(value="loginUser", method=RequestMethod.POST)
	public String loginUser(HttpSession session, HttpServletRequest request, Model model){
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		try {
			boolean exist=userDao.existsUser(username, password);
			if(exist){
				User u=userDao.getUser(username);
				session.setAttribute("user", u);
				session.setAttribute("logged", true);
				synchronized (model) {
					if(!model.containsAttribute("songs")){
						TreeSet<Song> songs = songDao.getAllSongs();
						model.addAttribute("songs", songs);
					}
					if(!model.containsAttribute("genres")){
						Map genres=genresDao.getAllGenres();
						model.addAttribute("genres", genres);
					}
				}
				return "main";
			}
			else{
				request.setAttribute("error", "User does not exist!");
				return "login";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "index";
		}
	}
	
	//ON CLICKING THE HOME BUTTON THIS METHOD RETURNS THE USER TO HIS MAIN PAGE
	@RequestMapping(value="homeButton", method=RequestMethod.GET)
	public String backToMain(){
		return "main";
	}
	
	//LOGGIN OUT AN USER
	@RequestMapping(value="logout", method=RequestMethod.POST)
	public String logoutUser(HttpSession session){
		session.invalidate();
		return "index";
	}
}
