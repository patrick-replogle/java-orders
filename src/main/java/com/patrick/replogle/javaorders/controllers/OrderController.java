package com.patrick.replogle.javaorders.controllers;

import com.patrick.replogle.javaorders.models.Order;
import com.patrick.replogle.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderController
{
    @Autowired
    OrderServices orderServices;

    @GetMapping(value = "/{id}", produces ={"application/json"})
    public ResponseEntity<?> findOrderById(@PathVariable long id)
    {
        Order o = orderServices.findOrderById(id);

        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{orderid}")
    public ResponseEntity<?> deleteOrderByName(@PathVariable long orderid)
    {
        orderServices.delete(orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
