package com.foodie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.foodie.model.FoodOrders;

@Service
public class Mail {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(FoodOrders foodOrder) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(foodOrder.getEmail());
		mail.setSubject("Your Jack's Yummy Deliveries Order!");
		mail.setText("Thanks " + foodOrder.getName() + " for placing your order!" + "\n\n"
				+ "Below are the details of your order:" + "\n" + printFoodList(foodOrder) + "\n" + "Delivery Address:"
				+ "\n" + "     " + foodOrder.getAddress() + "\n" + "     " + foodOrder.getCity() + ", "
				+ foodOrder.getState() + "\n" + "     " + foodOrder.getZip() + "\n" + "Delivery Date:" + "\n" + "     "
				+ foodOrder.getDeliveryDate());
		javaMailSender.send(mail);
	}

	private String printFoodList(FoodOrders foodOrder) {
		String foodList = "";
		List<String> tempFoodList = foodOrder.getOrderListItems();
		for (int i = 0; i < foodOrder.getOrderListItems().size(); i++) {
			foodList += ("     " + tempFoodList.get(i) + "\n");
		}
		return foodList;
	}

}
