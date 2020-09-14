package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Order;

public interface OrderServices
{
    Order findOrderById(long id);

    Order save(Order order);

    Order update(Order order, long id);

    void delete(long id);
}
