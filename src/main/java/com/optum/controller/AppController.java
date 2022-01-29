package com.optum.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.optum.model.FavouriteModel;
import com.optum.model.User;
import com.optum.service.FavouriteMovieService;
import com.optum.service.LoginService;

@RestController
public class AppController {
	

	@Autowired
	private FavouriteMovieService favouriteMovieService;

	@Autowired
	LoginService logInService;
	
	@PostMapping("/login")
	public String logIn(User user) {
		try {
			boolean isAdded = logInService.addUser(user);
			if (isAdded) {
				return "User is Added..!!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "User Not Added..!!";
	}

	@PostMapping("/signup")
	public String SignUp(User user) {

		try {
			if (logInService.signUpUser(user)) {
				return "SignUp Successfull";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SignUp Failed might be username or password is incorrect";
	}

	// http://localhost:9091/movieapp/deletefav/moviename
	@DeleteMapping("deletefav/{title}")
	public String deleteFavourite(@PathVariable("title") String title) {
		try {
			if (favouriteMovieService.deleteFavourite(title)) {
				return "Movie found in Favourite with the title " + title + " and deleted";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Movie not found to delete in Favourites Collection";
	}

	// http://localhost:9091/movieapp/addfav
	@PostMapping("/addToFavourites")
	public String addFavourite(@RequestBody FavouriteModel favouriteModel) {

		try {
			favouriteMovieService.addFavCollection(favouriteModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Movie has been Added into Favourite Collection";
	}

	
	@GetMapping("/test")
	public String test() {
		
		return "Hello";
	}
	
	
	@GetMapping("/results")
	@ResponseBody
	public ModelAndView searchMovies(@RequestParam("movie")String movieName) {
		String url = "http://www.omdbapi.com/";
		String charset = "UTF-8";
		String query;
		String inline = null;
		ModelAndView modelAndview = new ModelAndView();
		modelAndview.setViewName("resultsPage.html");

		try {
			query = String.format("t=%s&apikey=e742800f", URLEncoder.encode(movieName, charset));
			
			URL url1= new URL(url + "?" + query);
			HttpURLConnection conn = (HttpURLConnection)url1.openConnection(); 
			conn.setRequestMethod("GET");
			conn.connect();
			String msg = StreamUtils.copyToString( url1.openStream(), Charset.defaultCharset());
			String[] data = msg.split(",");
			
			for(String d : data) {
				if(d.contains("Title")) {
					modelAndview.addObject("Title", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Year")) {
					modelAndview.addObject("Year", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Rated")) {
					modelAndview.addObject("Rated", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Released")) {
					modelAndview.addObject("Released", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Actors")) {
					modelAndview.addObject("Actors", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Director")) {
					modelAndview.addObject("Director", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Writer")) {
					modelAndview.addObject("Writer", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Language")) {
					modelAndview.addObject("Language", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Country")) {
					modelAndview.addObject("Country", d.split(":")[1].replace("\"", ""));
				}
				if(d.contains("Poster")) {
					String[] poster = d.split(":");
					if(poster.length >2) {
					String posterImg = poster[1]+":"+poster[2];
					modelAndview.addObject("Poster", posterImg.substring(1 , posterImg.length()-1));
					}else {
						modelAndview.addObject("Poster", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQkmpiE3saxLv17jlQVpffuUAAtU95HJoaPRw&amp;usqp=CAU");
					}
				}
			}
			modelAndview.addObject("response", msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndview;
	}
}
