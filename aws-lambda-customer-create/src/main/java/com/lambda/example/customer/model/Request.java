package com.lambda.example.customer.model;

import com.lambda.example.customer.model.Customer;

public class Request {

    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
