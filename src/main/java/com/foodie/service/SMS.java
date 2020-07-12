package com.foodie.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMS {

	@Value("${twilio.account.sid}")
	private String accountSid;
	
	@Value("${twilio.auth.token}")
	private String authToken;
	
	@Value("${twilio.account.phone.number}")
	private String twilioAccountPhoneNum;
	
	public void sendSMS(Long receivingNum, String randomValidationKey) {		
		String finalReceivingNum = Long.toString(receivingNum);
		Twilio.init(accountSid, authToken);	
		Message.creator(
				new PhoneNumber("+1" + finalReceivingNum), //receiving phone #
				new PhoneNumber(twilioAccountPhoneNum), //Twilio phone #
				"Greetings! Thanks for placing an order with Jack's Yummy Deliveries. Please enter this code into your browser window to process your order. Thanks!     " 
						+ randomValidationKey).create();
	}
	
}


