package com.foodie.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="smsvalidationtempkey")
public class SMSValidationTempKey {

	@Id
	@GeneratedValue
	private long databaseID;
	
	private String name;
	
	private String smsTempKey;

	public long getDatabaseID() {
		return databaseID;
	}

	public String getSmsTempKey() {
		return smsTempKey;
	}

	public void setDatabaseID(long databaseID) {
		this.databaseID = databaseID;
	}

	public void setSmsTempKey(String smsTempKey) {
		this.smsTempKey = smsTempKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SMSValidationTempKey(String name, String smsTempKey) {
		super();
		this.name = name;
		this.smsTempKey = smsTempKey;
	}
	
	public SMSValidationTempKey() {}
	
	 
	
}
