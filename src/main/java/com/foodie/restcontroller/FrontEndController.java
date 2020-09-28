package com.foodie.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodie.model.FoodOrders;
import com.foodie.model.SMSValidationTempKey;
import com.foodie.repository.FoodOrderRepository;
import com.foodie.repository.SMSValidationTempKeyRepository;
import com.foodie.service.Mail;
import com.foodie.service.Misc;
import com.foodie.service.SMS;
import com.foodie.service.Validation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FrontEndController {

	@Autowired
	FoodOrderRepository foodOrderRepository;

	@Autowired
	SMSValidationTempKeyRepository smsvalidationrepository;

	@Autowired
	Mail mailservicer;

	@Autowired
	SMS smsMessageSender;

	@Autowired
	Misc restfulservicer;

	@Autowired
	Validation validator;

	@GetMapping(path = "/orders")
	public ResponseEntity<?> getFoodOrders() {
		List<FoodOrders> finalFoodOrderList = new ArrayList<FoodOrders>();
		List<FoodOrders> tempFoodOrdersList = foodOrderRepository.findAll();
		for (FoodOrders foodOrder : tempFoodOrdersList) {
			if (foodOrder.isFulfilled() == !true) {
				finalFoodOrderList.add(foodOrder);
			}
		}
		return new ResponseEntity<>(finalFoodOrderList, HttpStatus.OK);
	}

	@GetMapping(path = "/orders/{databaseID}")
	public FoodOrders getSingleFoodOrder(@PathVariable long databaseID) {
		Optional<FoodOrders> databaseFoodOrder = foodOrderRepository.findById(databaseID);
		return databaseFoodOrder.get();
	}

	@DeleteMapping(path = "/deleteorders/{databaseID}")
	public ResponseEntity<?> deleteFoodOrder(@PathVariable long databaseID) {
		foodOrderRepository.deleteById(databaseID);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/order-finished")
	public ResponseEntity<?> createFoodOrder(@RequestBody FoodOrders foodOrder) {
		if (validator.compareIncomingFoodOrderToSMSKey(foodOrder) && validator.areNewFoodOrderFieldsValid(foodOrder)) {
			restfulservicer.addOrderToDatabase(foodOrder);
			validator.deleteUsedSMSValidationKey(foodOrder.getName(), foodOrder.getSMSValidationKey());
			mailservicer.sendEmail(foodOrder);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(path = "/createTempSMSValidationKey")
	public ResponseEntity<?> createTempSMSValidationKey(@RequestBody FoodOrders foodOrder) {
		String tempRandomNum = restfulservicer.generateRandomNum();
		smsMessageSender.sendSMS(foodOrder.getPhone(), tempRandomNum);
		smsvalidationrepository.save(new SMSValidationTempKey(foodOrder.getName(), tempRandomNum));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/update-order/{databaseID}")
	public ResponseEntity<?> updateFoodOrder(@RequestBody FoodOrders foodOrder) {
		if (/* validator.areNewFoodOrderFieldsValid(foodOrder) */ true) {
			foodOrderRepository.save(foodOrder);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
