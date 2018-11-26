import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../model/customer';

@Injectable()
export class CustomerService {
  constructor(private http: HttpClient) { }
  customersUrl = 'http://localhost:8080/customer/';

  getConsumers() {
    return this.http.get(this.customersUrl);
  }

  newConsumer(customer: Customer) {
    return this.http.post(this.customersUrl, customer, { responseType: 'text' });
  }
}