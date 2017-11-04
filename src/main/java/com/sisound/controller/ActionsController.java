package com.sisound.controller;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sisound.model.User;
import com.sisound.model.db.ActionsDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;

@RestController
public class ActionsController {

	@Autowired
	SongDao songDao;
	@Autowired
	ActionsDao actionDao;
	@Autowired
	UserDao userDao;
	
	//LIKE/UNLIKE SONG
		@RequestMapping(value="restLikeSong", method=RequestMethod.POST)
		@ResponseBody
		public void likeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			long songId=Long.parseLong(req.getParameter("songId").toString());
			
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					if(!songDao.isSongLiked(songId, u.getUserID())){
						if(songDao.isSongDisliked(songId, u.getUserID())){
							actionDao.removeDislike(true, songId, u.getUserID());
						}
						actionDao.likeSong(songId, u.getUserID());
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		@RequestMapping(value="restUnlikeSong", method=RequestMethod.POST)
		@ResponseBody
		public void unlikeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			System.out.println(u.toString());
			long songId=Long.parseLong(req.getParameter("songId").toString());
			
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					if(songDao.isSongLiked(songId, u.getUserID())){
						actionDao.removeLike(true, songId, u.getUserID());
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//DISLIKE/UNDISLIKE SONG
		@RequestMapping(value="restDislikeSong", method=RequestMethod.POST)
		@ResponseBody
		public void dislikeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			long songId=Long.parseLong(req.getParameter("songId").toString());
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					if(!songDao.isSongDisliked(songId, u.getUserID())){
						if(songDao.isSongLiked(songId, u.getUserID())){
							actionDao.removeLike(true, songId, u.getUserID());
						}
						actionDao.dislikeSong(songId, u.getUserID());
						resp.setStatus(200);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		@RequestMapping(value="restUndislikeSong", method=RequestMethod.POST)
		@ResponseBody
		public void undislikeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			long songId=Long.parseLong(req.getParameter("songId").toString());
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					if(songDao.isSongDisliked(songId, u.getUserID())){
						actionDao.removeDislike(true, songId, u.getUserID());
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//FOLLOW/UNFOLLOW USER
		@RequestMapping(value="restFollowUser", method=RequestMethod.POST)
		@ResponseBody
		public void followUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			long followedId=Long.parseLong(req.getParameter("userId").toString());
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					HashMap<Long, Boolean> followed=userDao.getFollowedIds(u);
					if(followed.size()>0){
						if(!followed.containsKey(followedId)){
							userDao.followUser(u.getUserID(), followedId);
							resp.setStatus(200);
						}
					}
					else{
						userDao.followUser(u.getUserID(), followedId);
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		@RequestMapping(value="restUnfollowUser", method=RequestMethod.POST)
		@ResponseBody
		public void unfollowUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			long followedId=Long.parseLong(req.getParameter("userId").toString());
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					HashMap<Long, Boolean> followed=userDao.getFollowedIds(u);
					if(followed.size()>0){
						if(followed.containsKey(followedId)){
							userDao.unfollowUser(u.getUserID(), followedId);
							resp.setStatus(200);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
