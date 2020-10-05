package com.foodie.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodie.repository.FoodOrderRepository;

@Service
public class Misc {

	@Autowired
	FoodOrderRepository foodOrderRepository;
	
	public String generateRandomNum() {
		int number = new Random().nextInt(999999);
		return String.format("%06d", number);
	}



}
