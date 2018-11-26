package com.srm.asset.application.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.srm.asset.domain.model.Customer;
import com.srm.asset.domain.repository.SQLiteRepository;
import com.srm.asset.domain.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private final static Logger LOGGER = Logger.getLogger(CustomerController.class.getName());
	private final SQLiteRepository sqLiteRepository;
	private final CustomerService customerService;

	@Autowired
	public CustomerController(SQLiteRepository sqLiteRepository, CustomerService customerService) {
		this.sqLiteRepository = sqLiteRepository;
		this.customerService = customerService;
		InitDatabase();
	}

	@ApiOperation(value = "Persists a new customer")
	@RequestMapping(value = "/", method = RequestMethod.POST, produces =  MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveCustomer(
			@ApiParam(name = "customer", value = "Customer's data", required = true) @RequestBody Customer customer) {
		try {
			DecimalFormat f = new DecimalFormat("##.00");
			customer.fullName = customer.fullName.trim();
			customer.creditLimit = Double.parseDouble(f.format(customer.creditLimit));
			customer.risk = customer.risk.trim();

			return customerService.save(sqLiteRepository, customer);
		} catch (Exception e) {
			return logException(e);
		}
	}

	@ApiOperation(value = "Get the list of customers")
	@GetMapping(value = "/")
	public List<Customer> listCustomers() {
		return customerService.listCustomers(sqLiteRepository);
	}

	public String logException(Exception e) {
		LOGGER.log(Level.SEVERE, String.format("%s: %s", e.getClass().getName(), e.getMessage()), e);
		return String.format("%s: %s", e.getClass().getName(), e.getMessage());
	}

	public void InitDatabase() {
		sqLiteRepository.connect();

		if (!sqLiteRepository.findStructure("SELECT name FROM sqlite_master WHERE type='table' AND name='customer'")) {
			StringBuilder sql = new StringBuilder();
			sql.append("CREATE TABLE customer(");
			sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
			sql.append("fullName VARCHAR(150) NOT NULL,");
			sql.append("creditLimit DOUBLE NOT NULL,");
			sql.append("risk CHARACTER(1) NOT NULL,");
			sql.append("tax DOUBLE NOT NULL)");
			sqLiteRepository.executeNonQuery(sql.toString());
		}

		sqLiteRepository.disconnect();
	}
}
