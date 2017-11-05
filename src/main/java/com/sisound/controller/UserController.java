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
import com.sisound.model.db.ActionsDao;
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
	@Autowired
	ActionsDao actionDao;
	

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
				userDao.insertUser(u);
				session.setAttribute("sessionUser", u);
				session.setAttribute("logged", true);
				
				return "redirect:/index";
			} 
			else if(userDao.usernameExists(u.getUsername())){
				request.setAttribute("error", "Username is already taken");
				return "logReg";
			} 
			else {
				request.setAttribute("error", "E-mail already in use");
				return "logReg";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "errorPage";
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

//				session.setAttribute("likedSongs", u.getLikedSongs());
//				session.setAttribute("dislikedSongs", u.getDislikedSongs());
									
				return "redirect:/index";
			}
			else{
				request.setAttribute("error", "Wrong username or password!");
				return "logReg";
			}
		} catch (SQLException e) {
//			request.setAttribute("error", "database problem : " + e.getMessage());
			System.out.println(e.getMessage());
			return "errorPage";
		}
	}
	

		//profile
		@RequestMapping(value="profile{x}", method=RequestMethod.GET)
		public String profilePage(@PathVariable String x, Model model, HttpSession session){
			
			User currentUser = (User)session.getAttribute("sessionUser");
		
			try {
				User newUser = userDao.getUser(x);
				model.addAttribute("modelUser", newUser);
				
			} catch (SQLException e) {
				// TODO create error page
				return "errorPage";
			}

			return "profile2";
		}
		
	//back to main page
	@RequestMapping(value="homeButton", method=RequestMethod.GET)
	public String backToMain(Model model, HttpSession session){		
//		return "redirect:/index";
		LinkedHashSet<Song> sortedByLikes;
		try {
			sortedByLikes = songDao.getTop10();
			model.addAttribute("songsToShow", sortedByLikes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "comments";
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
					String coverPicPath = WebInitializer.LOCATION + "\\cover" + File.separator +  u.getUserID() + "." + FilenameUtils.getExtension(coverpic.getOriginalFilename());
					File profile = new File(coverPicPath);
					coverpic.transferTo(profile);
					u.setCoverPhoto(coverPicPath);	
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
			return "redirect:/index";

		}
		
//		//FOLLOW USER
//		@RequestMapping(value="followUser", method=RequestMethod.POST)
//		@ResponseBody
//		public void followUser(HttpServletRequest request, HttpServletResponse resp, HttpSession session){
//			User fwr=(User)session.getAttribute("sessionUser");
//			if(fwr==null){
//				resp.setStatus(401);
//			}
//			else{
//				try {
//					
//					String followed=(String)request.getParameter("followed");
//					System.out.println(followed);
//					User fwd=userDao.getUser(followed);
//					userDao.followUser(fwr.getUserID(), fwd.getUserID());
//					//fwr.getFollowedIds().add(fwd.getUserID());
//					
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				resp.setStatus(200);
//			}
//		}
//		
//		//UNFOLLOW USER
//		@RequestMapping(value="unfollowUser", method=RequestMethod.POST)
//		@ResponseBody
//		public void unfollowUser(HttpServletRequest request, HttpServletResponse resp, HttpSession session){
//			User fwr=(User)session.getAttribute("sessionUser");
//			String followed=(String)request.getParameter("followed");
//			if(fwr==null){
//				resp.setStatus(401);
//			}
//			else{
//				try {
//					User fwd=userDao.getUser(followed);
//					if(!userDao.getFollowedIds(fwr).contains(fwd.getUserID())){
//						System.out.println("NE MOJE DA SE OTSLEDVA");
//					}
//					userDao.unfollowUser(fwr.getUserID(), fwd.getUserID());
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				resp.setStatus(200);
//			}
//		}
		
		@RequestMapping(value="/likesong", method=RequestMethod.POST)
		public String likeSong(HttpServletRequest request, HttpSession session, @RequestParam(value = "song") long id /*, @RequestParam(value = "page") String page*/){
			
			User u = (User)session.getAttribute("sessionUser");
			String requestURL = request.getRequestURL().toString();
//			String url = request.getHeader();
			String[] str = requestURL.split("/");
			
			System.out.println(str[str.length - 1]);
		
			if(u == null){
				return "logReg";
			}
			else{
				try {
					actionDao.likeSong(id, u.getUserID());
					if(u.getDislikedSongs().containsKey(id)) {
						actionDao.removeDislike(true, id, u.getUserID());
						u.removeDislike(id);
					}
				} catch (SQLException e) {
					return "errorPage";
				}
				
				u.addLike(id);
				
				return "redirect:/index";
			}
		}
		
		@RequestMapping(value="/unlikesong", method=RequestMethod.POST)
		public String unlikeSong(HttpSession session, @RequestParam(value = "song") long id){
			
			User u = (User)session.getAttribute("sessionUser");
			
			if(u == null){
				return "logReg";
			}
			else{
				try {
					actionDao.removeLike(true, id, u.getUserID());
				} catch (SQLException e) {
					return "errorPage";
				}
				u.removeLike(id);
				
				return "redirect:/index";
//				return "index";
			}
		}
		
		@RequestMapping(value="/undislikesong", method=RequestMethod.POST)
		public String undislikeSong(HttpSession session, @RequestParam(value = "song") long id){
			
			User u = (User)session.getAttribute("sessionUser");
			
			if(u == null){
				return "logReg";
			}
			else{
				try {
					actionDao.removeDislike(true, id, u.getUserID());
				} catch (SQLException e) {
					return "errorPage";
				}
				u.removeDislike(id);
				
				return "redirect:/index";
			}
		}
		
		@RequestMapping(value="/dislikesong", method=RequestMethod.POST)
		public String dislikeSong(HttpSession session, @RequestParam(value = "song") long id){
			
			User u = (User)session.getAttribute("sessionUser");
		
			if(u == null){
				return "logReg";
			}
			else{
				try {
					actionDao.dislikeSong(id, u.getUserID());
					if(u.getLikedSongs().containsKey(id)) {
						actionDao.removeLike(true, id, u.getUserID());
						u.removeLike(id);
					}
				} catch (SQLException e) {
					return "errorPage";
				}
				
				u.addDislike(id);
				
				return "redirect:/index";
			}
		}
		
		@RequestMapping(value="/follow", method=RequestMethod.POST)
		public String followUser(HttpSession session, @RequestParam(value = "user") long id, @RequestParam(value = "name") String username){
			
			User u = (User)session.getAttribute("sessionUser");
		
			if(u == null){
				return "logReg";
			}
			else{
				try {
					userDao.followUser(u.getUserID(), id);
				} catch (SQLException e) {
					return "errorPage";
				}
				
				u.addFollowing(id);
				
				return "redirect:/profile" + username;
			}
		}
		
		@RequestMapping(value="/unfollow", method=RequestMethod.POST)
		public String unfollowUser(HttpSession session, @RequestParam(value = "user") long id, @RequestParam(value = "name") String username){
			
			User u = (User)session.getAttribute("sessionUser");
		
			if(u == null){
				return "logReg";
			}
			else{
				try {
					userDao.unfollowUser(u.getUserID(), id);
				} catch (SQLException e) {
					return "errorPage";
				}
				
				u.removeFollowing(id);
				
				return "redirect:/profile" + username;
			}
		}



}