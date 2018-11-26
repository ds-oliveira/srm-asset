package com.srm.asset.domain.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.srm.asset.domain.model.Customer;
import com.srm.asset.domain.model.RiskEnum;
import com.srm.asset.domain.repository.SQLiteRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final String fullNameRegex = "[A-Z][a-zãáé]+\\s{0,1}([A-Z][a-zãáé]+|(de|da|dos|do)\\s{0,1}[A-Z][a-zãáé]+)\\s{0,1}([A-Z][a-zãáé]+|(de|da|dos|do)\\s{0,1}[A-Z][a-zãáé]+)*[A-Za-zãáé\\s]*";
	private static final String creditLimitRegex = "^[0-9]*\\.[0-9]{1,2}$|[0-9]*";
	private static final Pattern fullNamePattern = Pattern.compile(fullNameRegex, Pattern.CASE_INSENSITIVE);
	private static final Pattern creditLimitPattern = Pattern.compile(creditLimitRegex, Pattern.CASE_INSENSITIVE);
	
	@Override
	public String save(SQLiteRepository sqLiteRepository, Customer customer) {

		validateModel(customer);
		customer.tax = calculateTax(customer);

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO Customer(fullName, creditLimit, risk, tax) VALUES (?,?,?,?)");

		Map<Integer, Object> params = new HashMap<Integer, Object>();
		params.put(1, customer.fullName);
		params.put(2, customer.creditLimit);
		params.put(3, customer.risk);
		params.put(4, customer.tax);

		sqLiteRepository.connect();
		sqLiteRepository.executeNonQuery(sql.toString(), params);
		sqLiteRepository.disconnect();
		return "success";
	}

	@Override
	public List<Customer> listCustomers(SQLiteRepository sqLiteRepository) {
		sqLiteRepository.connect();
		List<Customer> customers = sqLiteRepository.executeQuery("SELECT * FROM customer", Customer.class);
		sqLiteRepository.disconnect();
		return customers;
	}

	private double calculateTax(Customer customer) {
		if (customer.risk.equals(RiskEnum.B.getValue()))
			return 10;
		if (customer.risk.equals(RiskEnum.C.getValue()))
			return 20;
		return 0;
	}

	private void validateModel(Customer customer) {

		
		if (customer.fullName.isEmpty() || customer.fullName.length() > 150 || customer.fullName.length() < 3 || !fullNamePattern.matcher(customer.fullName).matches())
			throw new IllegalArgumentException("Please type a valid fullName");

		if (customer.creditLimit <= 0 || !creditLimitPattern.matcher(String.valueOf(customer.creditLimit)).matches())
			throw new IllegalArgumentException("Please type a valid creditLimit");

		boolean matchNone = Arrays.stream(RiskEnum.values()).filter(f -> f.toString().equals(customer.risk))
				.count() == 0;
		if (customer.risk.isEmpty() || customer.risk.length() > 1 || matchNone)
			throw new IllegalArgumentException("Please type a valid risk");
	}
}
