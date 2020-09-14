package com.patrick.replogle.javaorders.controllers;

import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.services.CustomerServices;
import com.patrick.replogle.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @DeleteMapping(value = "/customer/{custid}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custid)
    {
        customerServices.delete(custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{customerid}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();

        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{custid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFullCustomer(@PathVariable long custid, @Valid @RequestBody Customer updateCustomer)
    {
        updateCustomer.setCustcode(custid);

        updateCustomer = customerServices.save(updateCustomer);

        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{custid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updatePartCustomer(@PathVariable long custid, @RequestBody Customer updateCustomer)
    {

        updateCustomer = customerServices.update(updateCustomer, custid);

        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
}

