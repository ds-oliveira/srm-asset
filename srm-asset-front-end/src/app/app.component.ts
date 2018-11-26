import { Component } from '@angular/core';
import { Customer } from './model/customer';
import { CustomerService } from './service/customer.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [CustomerService]
})
export class AppComponent {
  constructor(private service: CustomerService) { }

  customer: Customer = {
    id: null,
    fullName: '',
    creditLimit: null,
    risk: 'A'
  };

  validation = {
    resultFullName: false,
    resultCreditLimit: false,
    resultRisk: false
  }

  errorMessages = null;
  rawCustomersList = [];

  ngOnInit() {
    this.getCustomersList();
  }

  send() {
    if(this.validate())
      this.registerNewCustomer();
  }

  registerNewCustomer() {
    this.service.newConsumer(this.customer).subscribe((data: any) => {
      this.customer.fullName = '';
      this.customer.creditLimit = null;
      this.customer.risk = 'A';
      document.getElementById("txtFullName").focus();
      this.getCustomersList();
    });
  }

  getCustomersList() {
    this.service.getConsumers().subscribe((data: any) => {
      this.rawCustomersList = data;
    });
  }

  validate() {
    if (this.customer.fullName)
      this.validation.resultFullName = /[A-Z][a-zãáé]+\s{0,1}([A-Z][a-zãáé]+|(de|da|dos|do)\s{0,1}[A-Z][a-zãáé]+)\s{0,1}([A-Z][a-zãáé]+|(de|da|dos|do)\s{0,1}[A-Z][a-zãáé]+)*[A-Za-zãáé\s]*/.test(this.customer.fullName);
    if (this.customer.creditLimit)
      this.validation.resultCreditLimit = /^[0-9]*\.[0-9]{1,2}$|[0-9]*/.test(this.customer.creditLimit.toString()) && this.customer.creditLimit > 0;
    if (this.customer.risk)
      this.validation.resultRisk = /^[ABC]{1}$/.test(this.customer.risk);

    let messages = [];
    if (!this.validation.resultFullName)
      messages.push("Full Name is Invalid");
    if (!this.validation.resultCreditLimit)
      messages.push("Credit Limit is Invalid");
    if (!this.validation.resultRisk)
      messages.push("Risk is Invalid");

    this.errorMessages = messages || null;
    return this.errorMessages.length === 0;
  }
}
