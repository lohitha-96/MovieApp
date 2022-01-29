package com.optum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optum.model.User;
import com.optum.repository.LogInRepository;

@Service
public class LoginService {

	@Autowired
	LogInRepository logInRepository;

	public boolean addUser(User user) throws Exception {

		User user1 = logInRepository.save(user);
		if (user1 == null) {
			return false;
		}
		return true;
	}

	public boolean signUpUser(User user)throws Exception  {

		User userFromDb = logInRepository.findByUserName(user.getUserName()).get();

		if (user.getUserName().equals(userFromDb.getUserName())
				&& user.getPassword().equals(userFromDb.getPassword())) {
			return true;
		}
		return false;

	}
}
