package com.sisound.controller;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sisound.model.User;
import com.sisound.model.db.ActionsDao;
import com.sisound.model.db.CommentDao;
import com.sisound.model.db.PlaylistDao;
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
	@Autowired
	PlaylistDao playlistDao;
	@Autowired
	CommentDao commentDao;
	
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
							u.removeDislike(songId);
						}
						actionDao.likeSong(songId, u.getUserID());
						
						u.addLike(songId);
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
			long songId=Long.parseLong(req.getParameter("songId").toString());
			
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					if(songDao.isSongLiked(songId, u.getUserID())){
						actionDao.removeLike(true, songId, u.getUserID());
						u.removeLike(songId);
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
						u.addDislike(songId);
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
						if(u.getLikedSongs().containsKey(songId)) {
							actionDao.removeLike(true, songId, u.getUserID());
							u.removeLike(songId);
						}
						u.removeDislike(songId);
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
						if(followed.containsKey(followedId) && !followed.get(followedId)){
							userDao.followUser(u.getUserID(), followedId);
							u.addFollowing(followedId);
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
						if(followed.containsKey(followedId) && followed.get(followedId)){
							userDao.unfollowUser(u.getUserID(), followedId);
							u.removeFollowing(followedId);
							resp.setStatus(200);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//ADDING A SONG TO PLAYLIST
		@RequestMapping(value="restAddToPlaylist", method=RequestMethod.POST)
		@ResponseBody
		public void addToPlaylist(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
			User u=(User) session.getAttribute("sessionUser");
			Long playlistId=Long.parseLong(req.getParameter("playlistId").toString());
			Long songId=Long.parseLong(req.getParameter("songId").toString());
			
			if(session.isNew() || u==null){
				resp.setStatus(401);
			}
			else{
				try {
					playlistDao.addSongToPlaylist(playlistId, songId);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//like comment
		@RequestMapping(value="restLikeComment", method=RequestMethod.POST)
		@ResponseBody
		public void likeSongComment(HttpServletRequest req, HttpServletResponse resp, HttpSession session,  @RequestParam(value = "isSong") boolean isSong){
			User u = (User) session.getAttribute("sessionUser");
			long songCommentId = Long.parseLong(req.getParameter("commentId").toString());
					
			if(session.isNew() || u == null){
				resp.setStatus(401);
				System.out.println("ne vliza");
			}
			else{
				try {
					if(!commentDao.isCommentLiked(isSong, songCommentId, u.getUserID())){
						if(commentDao.isCommentDisliked(isSong, songCommentId, u.getUserID())){
							actionDao.removeDislikeComment(isSong, songCommentId, u.getUserID());
							u.removeCommentDislike(isSong, songCommentId);
						}
						actionDao.likeComment(isSong, songCommentId, u.getUserID());

						u.addCommentLike(isSong, songCommentId);
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//dislike comment
		@RequestMapping(value="restDislikeComment", method=RequestMethod.POST)
		@ResponseBody
		public void dislikeSongComment(HttpServletRequest req, HttpServletResponse resp, HttpSession session,  @RequestParam(value = "isSong") boolean isSong){
			User u = (User) session.getAttribute("sessionUser");
			long songCommentId = Long.parseLong(req.getParameter("commentId").toString());
			
			if(session.isNew() || u == null){
				resp.setStatus(401);
			}
			else{
				try {
					if(!commentDao.isCommentDisliked(isSong, songCommentId, u.getUserID())){
						if(commentDao.isCommentLiked(isSong, songCommentId, u.getUserID())){
							actionDao.removeLikeComment(isSong, songCommentId, u.getUserID());
							u.removeCommentLike(isSong, songCommentId);
						}
						actionDao.dislikeComment(isSong, songCommentId, u.getUserID());

						u.addCommentDislike(isSong, songCommentId);
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//unlike comment
		@RequestMapping(value="restUnlikeComment", method=RequestMethod.POST)
		@ResponseBody
		public void unlikeComment(HttpServletRequest req, HttpServletResponse resp, HttpSession session,  @RequestParam(value = "isSong") boolean isSong){
			User u = (User) session.getAttribute("sessionUser");
			long songCommentId = Long.parseLong(req.getParameter("commentId").toString());
			
			if(session.isNew() || u == null){
				resp.setStatus(401);
			} 
			else {
				try {
					if(commentDao.isCommentLiked(isSong, songCommentId, u.getUserID())){
						actionDao.removeLikeComment(isSong, songCommentId, u.getUserID());
						u.removeCommentLike(isSong, songCommentId);
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//unlike comment
		@RequestMapping(value="restUndislikeComment", method=RequestMethod.POST)
		@ResponseBody
		public void undislikeComment(HttpServletRequest req, HttpServletResponse resp, HttpSession session,  @RequestParam(value = "isSong") boolean isSong){
			User u = (User) session.getAttribute("sessionUser");
			long songCommentId = Long.parseLong(req.getParameter("commentId").toString());
			
			if(session.isNew() || u == null){
				resp.setStatus(401);
			} 
			else {
				try {
					if(commentDao.isCommentDisliked(isSong, songCommentId, u.getUserID())){
						
						actionDao.removeDislikeComment(isSong, songCommentId, u.getUserID());
						u.removeCommentDislike(isSong, songCommentId);
						resp.setStatus(200);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
