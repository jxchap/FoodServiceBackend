package com.foodie.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodie.model.FoodOrders;
import com.foodie.model.SMSValidationTempKey;
import com.foodie.repository.SMSValidationTempKeyRepository;

@Service
public class Validation {

	@Autowired
	SMSValidationTempKeyRepository smsvalidationrepository;

	public boolean compareIncomingFoodOrderToSMSKey(FoodOrders foodOrder) {
		List<SMSValidationTempKey> tempKeyList = smsvalidationrepository.findAll();
		for (SMSValidationTempKey currentKey : tempKeyList) {
			if (currentKey.getName().equals(foodOrder.getName())
					&& currentKey.getSmsTempKey().equals(foodOrder.getSMSValidationKey())) {
				return true;
			}
		}
		return false;
	}

	public void deleteUsedSMSValidationKey(String userName, String smsKey) {
		List<SMSValidationTempKey> tempKeyList = smsvalidationrepository.findAll();
		for (SMSValidationTempKey currentKey : tempKeyList) {
			if (currentKey.getName().equals(userName) && currentKey.getSmsTempKey().equals(smsKey)) {
				smsvalidationrepository.delete(currentKey);
			}
		}
	}

	public boolean areNewFoodOrderFieldsValid(FoodOrders foodOrder) // throws Exception
	{
		if (!evaluateFoodOrderName(foodOrder.getName()))
			return false;
		if (!evaluateFoodOrderEmail(foodOrder.getEmail()))
			return false;
		if (!evaluateFoodOrderPhone(foodOrder.getPhone()))
			return false;
		if (!evaluateFoodOrderAddress(foodOrder.getAddress()))
			return false;
		if (!evaluateFoodOrderCity(foodOrder.getCity()))
			return false;
		if (!evaluateFoodOrderState(foodOrder.getState()))
			return false;
		if (!evaluateFoodOrderZip(foodOrder.getZip()))
			return false;
		if (!evaluateFoodOrderDeliveryDate(foodOrder.getDeliveryDate())) return false;
		if (!evaluateFoodOrder_OrderListItems(foodOrder.getOrderListItems())) return false;
		return true;
	}

	private static boolean evaluateFoodOrderName(String name) 	{
		return name.matches("[a-zA-Z\\s]+");
	}

	private static boolean evaluateFoodOrderEmail(String email) {
		return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}

	private static boolean evaluateFoodOrderPhone(long phone) {
		String phoneString = "" + phone;
		return phoneString.matches("\\d{10}");
	}

	private static boolean evaluateFoodOrderAddress(String address) {
		return address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	}

	private static boolean evaluateFoodOrderCity(String city) {
		return city.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	}

	private static boolean evaluateFoodOrderState(String state) {
		return state.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	}

	private static boolean evaluateFoodOrderZip(long zip) {
		String zipString = "" + zip;
		return zipString.matches("\\d{5}");
	}

	private static boolean evaluateFoodOrderDeliveryDate(LocalDate deliveryDate) {
		try {
			LocalDate.parse(deliveryDate.toString());
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	private static boolean evaluateFoodOrder_OrderListItems(ArrayList<String> orderListItems) {
		if (orderListItems.isEmpty()) {System.out.println("invalid foodList"); return false;}
//		for (String foodItem : orderListItems) {
//			if (!foodItem.matches("[a-zA-Z\\s]+")) return false;
//		}
		return true;
	}

}
