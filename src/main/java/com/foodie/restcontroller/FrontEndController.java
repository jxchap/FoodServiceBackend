package com.foodie.restcontroller;

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
import com.foodie.service.RepositoryService;
import com.foodie.service.SMS;
import com.foodie.service.Validation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FrontEndController {

	@Autowired
	FoodOrderRepository foodOrderRepository;

	@Autowired
	RepositoryService respositoryService;

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
		
		return new ResponseEntity<>(respositoryService.getUnfulfilledFoodOrders(), HttpStatus.OK);
		
	}

	@GetMapping(path = "/orders/{databaseID}")
	public ResponseEntity<?> getSingleFoodOrder(@PathVariable long databaseID) {

		return new ResponseEntity<>(respositoryService.getSingleFoodOrder(databaseID), HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteorders/{databaseID}")
	public ResponseEntity<?> deleteFoodOrder(@PathVariable long databaseID) {
		respositoryService.deleteFoodOrder(databaseID);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/order-finished")
	public ResponseEntity<?> createFoodOrder(@RequestBody FoodOrders foodOrder) {
		if (validator.compareIncomingFoodOrderToSMSKey(foodOrder) && validator.areNewFoodOrderFieldsValid(foodOrder)) {
			respositoryService.addOrderToDatabase(foodOrder);
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
