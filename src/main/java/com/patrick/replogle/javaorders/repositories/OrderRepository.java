package com.patrick.replogle.javaorders.repositories;

import com.patrick.replogle.javaorders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{

}
