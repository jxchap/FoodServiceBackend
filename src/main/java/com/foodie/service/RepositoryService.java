package com.foodie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodie.model.FoodOrders;
import com.foodie.repository.FoodOrderRepository;

@Service
public class RepositoryService {

	@Autowired
	FoodOrderRepository foodOrderRepository;
	
	@Autowired
	Validation validator;

	public List<FoodOrders> getUnfulfilledFoodOrders() {
		List<FoodOrders> finalFoodOrderList = new ArrayList<>();
		List<FoodOrders> tempFoodOrdersList = foodOrderRepository.findAll();
		for (FoodOrders foodOrder : tempFoodOrdersList) {
			if (!foodOrder.isFulfilled()) {
				finalFoodOrderList.add(foodOrder);
			}
		}
		return finalFoodOrderList;
	}

	public FoodOrders getSingleFoodOrder(long databaseID) {

		Optional<FoodOrders> databaseFoodOrder = foodOrderRepository.findById(databaseID);
		return databaseFoodOrder.get();

	}

	public void deleteFoodOrder(long databaseID) {

		foodOrderRepository.deleteById(databaseID);

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
