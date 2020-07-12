package com.foodie.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="foodorders")
public class FoodOrders {

	@Id
	@GeneratedValue
	private long databaseID;

	private String name;
	private String email;
	private long phone;
	private String address;
	private String city;
	private String state;
	private long zip;
	private LocalDate deliveryDate;
	private boolean isFulfilled;
	private String foodItem;
	
	@Transient
	private ArrayList<String> orderListItems;
	@Transient
	private String SMSValidationKey;

	protected FoodOrders() {
	}
	
	/* ----------------------------------------------- */
	/* Getters and Setters */
	/* ----------------------------------------------- */

	public long getDatabaseID() {
		return databaseID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public long getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public long getZip() {
		return zip;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public ArrayList<String> getOrderListItems() {
		return orderListItems;
	}

	public boolean isFulfilled() {
		return isFulfilled;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public void setDatabaseID(long databaseID) {
		this.databaseID = databaseID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(long zip) {
		this.zip = zip;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setOrderListItems(ArrayList<String> orderListItems) {
		this.orderListItems = orderListItems;
	}

	public void setFulfilled(boolean isFulfilled) {
		this.isFulfilled = isFulfilled;
	}

	public void setFoodItem(String foodItem) {
		this.foodItem = foodItem;
	}

	public String getSMSValidationKey() {
		return SMSValidationKey;
	}

	public void setSMSValidationKey(String sMSValidationKey) {
		SMSValidationKey = sMSValidationKey;
	}

	
	
}
