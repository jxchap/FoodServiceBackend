package com.foodie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodie.model.FoodOrders;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrders, Long> {

}
