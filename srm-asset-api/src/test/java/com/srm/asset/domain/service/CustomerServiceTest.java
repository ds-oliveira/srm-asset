package com.srm.asset.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.srm.asset.domain.model.Customer;
import com.srm.asset.domain.model.RiskEnum;
import com.srm.asset.domain.repository.SQLiteRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
	@Mock
	SQLiteRepository repository;

	@Test
	public void shouldInsertNewConsumer() {
		Mockito.when(repository.connect()).thenReturn(true);
		Mockito.when(repository.executeNonQuery(Mockito.any(), Mockito.any())).thenReturn(true);
		Mockito.when(repository.disconnect()).thenReturn(true);
		
		Customer customer = new Customer();
		customer.fullName = "Danilo Silva de Oliveira";
		customer.creditLimit = 200000.00;
		customer.risk = RiskEnum.B.getValue();
		
		CustomerService service = new CustomerServiceImpl();
		assertEquals("success", service.save(repository, customer));
		verify(repository).connect();
		verify(repository).executeNonQuery(Mockito.any(), Mockito.any());
		verify(repository).disconnect();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotInsertNewConsumer(){
		Mockito.when(repository.connect()).thenReturn(true);
		Mockito.when(repository.executeNonQuery(Mockito.any(), Mockito.any())).thenReturn(true);
		Mockito.when(repository.disconnect()).thenReturn(true);
		
		Customer customer = new Customer();
		customer.fullName = "Danilo Silva de Oliveira";
		customer.creditLimit = -100;
		customer.risk = RiskEnum.B.getValue();
		
		CustomerService service = new CustomerServiceImpl();
		assertEquals("success", service.save(repository, customer));
		verify(repository).connect();
		verify(repository).executeNonQuery(Mockito.any(), Mockito.any());
		verify(repository).disconnect();
	}
	
	@Test
	public void shouldReturnConsumers() {
		List<Object> customersReturned = new ArrayList<Object>();
		
		Customer customer1 = new Customer();
		customer1.fullName = "Danilo Silva de Oliveira";
		customer1.creditLimit = 15000;
		customer1.risk = RiskEnum.B.getValue();
		
		Customer customer2 = new Customer();
		customer2.fullName = "Gustavo Pereira Alves";
		customer2.creditLimit = 4500000;
		customer2.risk = RiskEnum.C.getValue();
		
		customersReturned.add(customer1);
		customersReturned.add(customer2);
		
		Mockito.when(repository.connect()).thenReturn(true);
		Mockito.when(repository.executeQuery(Mockito.anyString(), Mockito.any()))
		.thenReturn(customersReturned);
		Mockito.when(repository.disconnect()).thenReturn(true);
		
		CustomerService service = new CustomerServiceImpl();
		assertTrue(service.listCustomers(repository).size() == 2);
		verify(repository).connect();
		verify(repository).executeQuery(Mockito.anyString(), Mockito.any());
		verify(repository).disconnect();
	}
	
	@Test
	public void shouldNotReturnConsumers() {
		List<Object> customersReturned = new ArrayList<Object>();
		
		Mockito.when(repository.connect()).thenReturn(true);
		Mockito.when(repository.executeQuery(Mockito.anyString(), Mockito.any()))
		.thenReturn(customersReturned);
		Mockito.when(repository.disconnect()).thenReturn(true);

		CustomerService service = new CustomerServiceImpl();
		assertTrue(service.listCustomers(repository).size() == 0);
		verify(repository).connect();
		verify(repository).executeQuery(Mockito.anyString(), Mockito.any());
		verify(repository).disconnect();
	}
}
