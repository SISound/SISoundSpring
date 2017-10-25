package com.sisound.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sisound.model.User;


@WebServlet("/edit_profile")
@MultipartConfig
public class EditProfileServlet extends HttpServlet {
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
	
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String description = request.getParameter("description");
		String city = request.getParameter("city");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String newPassword2 = request.getParameter("newPassword2");
		String oldEmail = request.getParameter("oldEmail");
		String newEmail = request.getParameter("newEmail");
		String newEmail2 = request.getParameter("newEmail2");
	
		User currentUser = (User) request.getSession().getAttribute("user");
		
	}

	
}
