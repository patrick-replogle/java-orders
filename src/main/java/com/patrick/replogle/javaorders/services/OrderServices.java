package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Order;

public interface OrderServices
{
    Order findOrderById(long id);

    Order save(Order order);
}
