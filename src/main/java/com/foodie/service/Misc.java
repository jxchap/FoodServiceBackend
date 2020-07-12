package com.foodie.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodie.model.FoodOrders;
import com.foodie.repository.FoodOrderRepository;

@Service
public class Misc {

	@Autowired
	FoodOrderRepository foodOrderRepository;
	
	public String generateRandomNum() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public void addOrderToDatabase(FoodOrders foodOrder) {
		List<String> tempListItemArray = foodOrder.getOrderListItems();
		for (int i = 0; i < foodOrder.getOrderListItems().size(); i++) {
			foodOrder.setFoodItem(tempListItemArray.get(i));
			foodOrder.setDatabaseID(foodOrder.getDatabaseID() + 1);
			foodOrderRepository.save(foodOrder);
		}
	}
	

	

	

}
