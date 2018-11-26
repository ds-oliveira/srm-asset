package com.srm.asset.domain.model;

public class Customer {
	//Not encapsulated because of the reflection using at SQLiteRepositoryImpl class
	public int id;
	public String fullName;
	public double creditLimit;
	public String risk;
	public double tax;
	
	@Override
	public String toString() {
		return String.format("Customer[id=%d, fullName='%s', creditLimit=%.2f,risk='%s',tax='%f']",
				id, 
				fullName,
				creditLimit,
				risk,
				tax);
	}
}