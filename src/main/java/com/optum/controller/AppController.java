package com.optum.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	@DeleteMapping("deletefav/{title}")
	@ResponseBody
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
	@RequestMapping(value="/addToFavourites")
	public void addFavourite(@RequestBody FavouriteModel favouriteModel) {
		System.out.println("**************** fav model Title :" + favouriteModel.getTitle());
		try {
			favouriteMovieService.addFavCollection(favouriteModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "Movie has been Added into Favourite Collection";
	}

	@GetMapping("/results")
	@ResponseBody
	public ModelAndView searchMovies(@RequestParam("movie") String movieName) {
		String url = "http://www.omdbapi.com/";
		String charset = "UTF-8";
		String query;
		String inline = null;
		ModelAndView modelAndview = new ModelAndView();
		modelAndview.setViewName("resultsPage.html");

		try {
			query = String.format("s=%s&apikey=e742800f", URLEncoder.encode(movieName, charset));

			URL url1 = new URL(url + "?" + query);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			String msg = StreamUtils.copyToString(url1.openStream(), Charset.defaultCharset());
			System.out.println("****************************" + msg);
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
			modelAndview.addObject("favouriteModel", new FavouriteModel());
			modelAndview.addObject("movielist", movie);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndview;
	}
}