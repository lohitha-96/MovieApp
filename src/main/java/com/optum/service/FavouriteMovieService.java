package com.optum.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.optum.model.FavouriteModel;

@Service
public class FavouriteMovieService {

	private ArrayList<FavouriteModel> favouriteList = new ArrayList<>();

	public void addFavCollection(FavouriteModel favourateRequest) throws Exception {
		favouriteList.add(favourateRequest);
		
	}
	
	public ArrayList<FavouriteModel> getAllFavouriteMovieCollection() {
		return favouriteList;
	}

	public boolean deleteFavourite(String title) throws Exception {
		for (FavouriteModel user : favouriteList) {
			if (user.getTitle().equals(title)) {
				favouriteList.remove(user);
				return true;
			}
		}
		return false;
	}

}
