package com.optum.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.optum.model.FavouriteModel;
import com.optum.model.Movie;
import com.optum.model.User;
import com.optum.service.FavouriteMovieService;
//import com.optum.service.LoginService;
import com.optum.service.LoginService;

@Controller
//@RequestMapping("/movieapp")
public class AppController {

	@Autowired
	private FavouriteMovieService favouriteMovieService;

	@Autowired
	private LoginService logInService;
	
	@PostMapping("/login")
	public String logIn(User user) {
		try {
			boolean isAdded = logInService.addUser(user);
			if (isAdded) {
				return "loginsuccess";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "User Not Added..!!";
	}

	@PostMapping("/signup")
	//@ResponseBody
	public String SignUp(User user) {

		try {
			if (logInService.signUpUser(user)) {
				return "resultsPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SignUp Failed might be username or password is incorrect";
	}

	// http://localhost:9091/movieapp/deletefav/moviename
	@PostMapping("/deletefav")
	public String deleteFavourite(FavouriteModel favouriteModel, Model model) {
		try {
			if (favouriteMovieService.deleteFavourite(favouriteModel.getTitle())) {
				model.addAttribute("favouritesmovies", favouriteMovieService.getAllFavouriteMovieCollection());
				model.addAttribute("delMovie", new FavouriteModel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "favourites";
	}

	// http://localhost:9091/movieapp/addfav
	@PostMapping(value="/addToFavourites")
	public String addFavourite(FavouriteModel favouriteModel  , Model model) {
		try {
			favouriteMovieService.addFavCollection(favouriteModel);
			model.addAttribute("favouritesmovies", favouriteMovieService.getAllFavouriteMovieCollection());
			model.addAttribute("delMovie", new FavouriteModel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "favourites";
	}

	@GetMapping("/results")
	public String searchMovies(@RequestParam("movie") String movieName , Model model) {
		String url = "http://www.omdbapi.com/";
		String charset = "UTF-8";
		String query;

		try {
			query = String.format("s=%s&apikey=e742800f", URLEncoder.encode(movieName, charset));

			URL url1 = new URL(url + "?" + query);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			String msg = StreamUtils.copyToString(url1.openStream(), Charset.defaultCharset());
			//System.out.println("****************************" + msg);
			String[] movieArray = msg.split("\\[");
			String[] movieSummary = movieArray[1].split("\\}");
			ArrayList<Movie> movie = new ArrayList<>();

			for (String summary : movieSummary) {
				String[] data = summary.split(",");
				Movie movies = new Movie();
				for (String d : data) {

					if (d.contains("Title")) {
						movies.setTitle(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Year")) {
						movies.setYear(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Type")) {
						movies.setRated(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Released")) {
						movies.setReleased(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Actors")) {
						movies.setActors(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Director")) {
						movies.setDirector(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Writer")) {
						movies.setWriter(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Language")) {
						movies.setLanguage(d.split(":")[1].replace("\"", ""));
					}
					if (d.contains("Poster")) {
						String[] poster = d.split(":");
						if (poster.length > 2) {
							String posterImg = poster[1] + ":" + poster[2];
							movies.setPoster(posterImg.substring(1, posterImg.length() - 1));
						} else {
							movies.setPoster(
									"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQkmpiE3saxLv17jlQVpffuUAAtU95HJoaPRw&amp;usqp=CAU");
						}
					}
				}
				if (movies.getTitle() != null) {
					movie.add(movies);
				}
			}
			model.addAttribute("favouriteModel", new FavouriteModel());
			model.addAttribute("movielist", movie);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "resultsPage";
	}
}