package com.foodie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodie.model.SMSValidationTempKey;

@Repository
public interface SMSValidationTempKeyRepository extends JpaRepository<SMSValidationTempKey, Long>{

}