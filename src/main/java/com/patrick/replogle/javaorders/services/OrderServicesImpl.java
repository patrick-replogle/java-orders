package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Order;
import com.patrick.replogle.javaorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrderRepository ordersrepos;

    @Override
    public Order findOrderById(long id)
    {
        return ordersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
    }

    @Override
    public Order save(Order order)
    {
        return ordersrepos.save(order);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (ordersrepos.findById(id).isPresent())
        {
            ordersrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Order " + id + " was not found!");
        }
    }
}
