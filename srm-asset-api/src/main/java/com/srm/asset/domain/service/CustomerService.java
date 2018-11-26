package com.srm.asset.domain.service;

import java.util.List;

import com.srm.asset.domain.model.Customer;
import com.srm.asset.domain.repository.SQLiteRepository;

public interface CustomerService {
	String save(SQLiteRepository sqLiteRepository, Customer customer);
	List<Customer> listCustomers(SQLiteRepository sqLiteRepository);
}