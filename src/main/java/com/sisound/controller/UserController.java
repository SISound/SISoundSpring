package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.sisound.WebInitializer;
import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.CountryDao;
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
	@Autowired
	CountryDao countryDao;
	

	//register
//	@RequestMapping(value="regPage", method = RequestMethod.GET)
//	public String addUser(Model m){
//		User u = new User();
//		m.addAttribute("user", u);
//		return "register";
//	}

	
	@RequestMapping(value="registerUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute User u, HttpServletRequest request, HttpSession session){
				
		try {
			if(!userDao.usernameExists(u.getUsername()) && !userDao.emailExists(u.getEmail())){
//				System.out.println(u.getUsername());
//				System.out.println(u.getPassword());
//				System.out.println(u.getEmail());
				userDao.insertUser(u);
				session.setAttribute("sessionUser", u);
				session.setAttribute("logged", true);
				
				synchronized (session) {
					if(session.getAttribute("songs") == null){
						HashSet<Song> songs = songDao.getTop10();
						session.setAttribute("songs", songs);
					}
					if(session.getAttribute("genres") == null){
						Map genres=genresDao.getAllGenres();
						session.setAttribute("genres", genres);
					}
				}
				
				return "main";
			} 
			else if(userDao.usernameExists(u.getUsername())){
				request.setAttribute("error", "username is taken");
				return "logReg";
			} 
			else {
				request.setAttribute("error", "e-mail already in use");
				return "logReg";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "index";
		}		
	}

	//LOGIN USER
	@RequestMapping(value="loginPage", method=RequestMethod.GET)
	public String loginPage(Model m){
		User u = new User();
		m.addAttribute("user", u);
		return "logReg";
	}
	
	//TODO CHECK LOGIN
	@RequestMapping(value="loginUser", method=RequestMethod.POST)
	public String loginUser(HttpSession session, HttpServletRequest request, Model model){

		String username=request.getParameter("username");
		String password=request.getParameter("password");
				
		try {
			boolean exist = userDao.existsUser(username, password);
			if(exist){
				
				User u = userDao.getUser(username);
				System.out.println(u.getUsername());
				session.setAttribute("sessionUser", u);
				session.setAttribute("logged", true);
			//	session.setAttribute("followed", userDao.getFollowedIds(u));
				//request.getSession().setAttribute("user1", u);
				
				HashSet<Song> songs = songDao.getTop10();
				
				if(session.getAttribute("songs") == null){
					session.setAttribute("songs", songs);
				}
				
				TreeSet<Song> sortedByDate=new TreeSet<>((o1, o2)->(o1.getUploadDate().compareTo(o2.getUploadDate()))*-1);
				sortedByDate.addAll(songs);
				if(session.getAttribute("sortedByDate") == null){
					session.setAttribute("sortedByDate", sortedByDate);
				}
				
				LinkedHashSet<Song> sortedByLikes=new LinkedHashSet<>();
				sortedByLikes.addAll(songs);
				if(session.getAttribute("sortedByLikes")==null){
					session.setAttribute("sortedByLikes", sortedByLikes);
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
	

		//profile
		@RequestMapping(value="profile{x}", method=RequestMethod.GET)
		public String profilePage(@PathVariable String x, Model model, HttpSession session){
			
			User currentUser = (User)session.getAttribute("sessionUser");
			
			if(x == currentUser.getName()) {
				model.addAttribute("modelUser", currentUser);
				session.setAttribute("avatar", currentUser.getProfilPicture());
			}
			else {			
				try {
					User newUser = userDao.getUser(x);
					model.addAttribute("modelUser", newUser);
					session.setAttribute("avatar", newUser.getProfilPicture());
				} catch (SQLException e) {
					// TODO create error page
					return "errorPage";
				}
			}
			return "profile2";
		}
		
	//ON CLICKING THE HOME BUTTON THIS METHOD RETURNS THE USER TO HIS MAIN PAGE
				@RequestMapping(value="homeButton", method=RequestMethod.GET)
				public String backToMain(Model model, HttpSession session){
					try {
							HashSet<Song> songs;
							songs = songDao.getTop10();
							session.setAttribute("songs", songs);
							
							TreeSet<Song> sortedByDate=new TreeSet<>((o1, o2)->(o1.getUploadDate().compareTo(o2.getUploadDate()))*-1);
							sortedByDate.addAll(songs);
							session.setAttribute("sortedByDate", sortedByDate);
							
							
							TreeSet<Song> sortedByLikes=new TreeSet<>((o1, o2)->Integer.compare(o1.getLikesCount(), o2.getLikesCount()));
							sortedByLikes.addAll(songs);
							session.setAttribute("sortedByLikes", sortedByLikes);
							
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return "main";
				}
	
	//get edit profile page
	@RequestMapping(value="editProfile", method = RequestMethod.GET)
	public String addUser(Model m, HttpSession session){
		
		User u = (User)session.getAttribute("sessionUser");
		m.addAttribute("user", u);
		
		try {
			m.addAttribute("countries", countryDao.getCountries());
		} catch (SQLException e) {
			e.printStackTrace();
			return "errorPage";
		}
		
		return "edit_profile";
	}
	
	//edit profile
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String editProfile(Model model, HttpServletRequest request, HttpSession session, 
			@ModelAttribute User u, @RequestParam("profilepic") MultipartFile profilepic, @RequestParam("coverpic") MultipartFile coverpic) {
		
		try {			
			if(u != null){
				if(profilepic != null && (FilenameUtils.getExtension(profilepic.getOriginalFilename()) != "")) {
					String profilePicPath = WebInitializer.LOCATION + "\\profile" + File.separator +  u.getUserID() + "." + FilenameUtils.getExtension(profilepic.getOriginalFilename());
					File profile = new File(profilePicPath);
					profilepic.transferTo(profile);
					u.setProfilPicture(profilePicPath);					
				}
				
				if(coverpic != null && (FilenameUtils.getExtension(coverpic.getOriginalFilename()) != "")) {
					File cover = new File(WebInitializer.LOCATION + "\\cover" + File.separator +  u.getUserID() + "." + FilenameUtils.getExtension(coverpic.getOriginalFilename()));
					coverpic.transferTo(cover);
					u.setCoverPhoto(coverpic.getOriginalFilename());
				}
				
				userDao.editProfile(u);
				session.removeAttribute("sessionUser");
				session.setAttribute("sessionUser", u);
				model.addAttribute("modelUser", u);
				
				return "profile2";
			} 
			else {
				return "logReg";
			}
		} catch (SQLException | IllegalStateException | IOException e) {
			e.printStackTrace();
			return "errorPage";
		}		
	}
	

		//LOGGIN OUT AN USER
		@RequestMapping(value="logout", method=RequestMethod.POST)
		public String logoutUser(HttpSession session){
			session.invalidate();
			return "index";

		}
		
		//FOLLOW USER
		@RequestMapping(value="followUser", method=RequestMethod.POST)
		@ResponseBody
		public void followUser(HttpServletRequest request, HttpServletResponse resp, HttpSession session){
			User fwr=(User)session.getAttribute("sessionUser");
			if(fwr==null){
				resp.setStatus(401);
			}
			else{
				try {
					
					String followed=(String)request.getParameter("followed");
					System.out.println(followed);
					User fwd=userDao.getUser(followed);
					userDao.followUser(fwr.getUserID(), fwd.getUserID());
					fwr.getFollowedIds().add(fwd.getUserID());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resp.setStatus(200);
			}
		}
		
		//UNFOLLOW USER
		@RequestMapping(value="unfollowUser", method=RequestMethod.POST)
		@ResponseBody
		public void unfollowUser(HttpServletRequest request, HttpServletResponse resp, HttpSession session){
			User fwr=(User)session.getAttribute("sessionUser");
			String followed=(String)request.getParameter("followed");
			if(fwr==null){
				resp.setStatus(401);
			}
			else{
				try {
					User fwd=userDao.getUser(followed);
					userDao.unfollowUser(fwr.getUserID(), fwd.getUserID());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resp.setStatus(200);
			}
		}

}