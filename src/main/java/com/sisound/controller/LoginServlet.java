package com.sisound.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.GenresDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try {
			boolean exist=UserDao.getInstance().existsUser(username, password);
			if(exist){
				User u=UserDao.getInstance().getUser(username);
				request.getSession().setAttribute("user", u);
				ServletContext application = getServletConfig().getServletContext();
				synchronized (application) {
					if(application.getAttribute("songs") == null){
						TreeSet<Song> songs = SongDao.getInstance().getAllSongs();
						application.setAttribute("songs", songs);
					}
					if(application.getAttribute("genres") == null){
						Map genres=GenresDao.getInstance().getAllGenres();
						application.setAttribute("genres", genres);
					}
				}
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
			else{
				request.setAttribute("error", "User does not exist!");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			//request.getRequestDispatcher("index.jsp").forward(request, response);
			response.getWriter().append(request.getAttribute("error").toString());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")!=null){
			ServletContext application = getServletConfig().getServletContext();
			try {
				synchronized (application) {
					TreeSet<Song> songs;
					songs = SongDao.getInstance().getAllSongs();
					application.setAttribute("songs", songs);
					if(application.getAttribute("genres") == null){				
						Map genres=GenresDao.getInstance().getAllGenres();
						application.setAttribute("genres", genres);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}
		else{
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
}
