package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SongServlet")
public class SongServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		System.out.println(request.getParameter("songName")!=null);
		File myFile = new File(UploadSongServlet.SONG_URL+request.getParameter("songName"));
				
		try (OutputStream out = response.getOutputStream()) {
			   Path path = myFile.toPath();
			   Files.copy(path, out);
			   out.flush();
		} catch (IOException e) {
			   e.printStackTrace();
		}
	}
}
