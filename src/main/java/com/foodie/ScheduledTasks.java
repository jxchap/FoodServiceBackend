package com.foodie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.foodie.repository.SMSValidationTempKeyRepository;

@Component
public class ScheduledTasks {

	@Autowired
	SMSValidationTempKeyRepository smsvalidationrepository;

	@Scheduled(fixedRate = 600000, initialDelay = 5000)
	public void sweepSMSTable() {
		smsvalidationrepository.deleteAll();
	}

}
