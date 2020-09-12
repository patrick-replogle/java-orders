package com.patrick.replogle.javaorders.controllers;

import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.services.CustomerServices;
import com.patrick.replogle.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomerOrders()
    {
        List<Customer> custList = customerServices.findAllCustomers();

        return new ResponseEntity<>(custList, HttpStatus.OK);

    }

    // http://localhost:2019/customers/{id}
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerById(@PathVariable long id)
    {
        Customer c = customerServices.findCustomerById(id);

        return new ResponseEntity<>(c, HttpStatus.OK);

    }

    // http://localhost:2019/customers/likename/{subname}
    @GetMapping(value = "/likename/{subname}", produces = {"application/json"})
    public ResponseEntity<?> findByLikeName(@PathVariable String subname)
    {
        List<Customer> rtnList = customerServices.findByLikeName(subname);

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = {"application/json"})
    public ResponseEntity<?> findOrderCount()
    {
        List <OrderCounts> rtnList = customerServices.findOrderCount();

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
}

