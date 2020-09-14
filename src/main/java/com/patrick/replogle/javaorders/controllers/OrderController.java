package com.patrick.replogle.javaorders.controllers;

import com.patrick.replogle.javaorders.models.Order;
import com.patrick.replogle.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{orderid}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();

        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{orderid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFullOrder(@PathVariable long orderid, @Valid @RequestBody Order updateOrder)
    {
        updateOrder.setOrdnum(orderid);

        updateOrder = orderServices.save(updateOrder);

        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @PatchMapping(value = "/order/{orderid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updatePartOrder(@PathVariable long orderid, @RequestBody Order updateOrder)
    {
        updateOrder = orderServices.update(updateOrder, orderid);

        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }
}
