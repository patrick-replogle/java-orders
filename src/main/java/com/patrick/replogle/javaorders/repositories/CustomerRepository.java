package com.patrick.replogle.javaorders.repositories;

import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    List<Customer> findByCustnameContainingIgnoringCase(String  subname);

    @Query(value = "SELECT c.custname name, count(o.ordnum) countorders " +
            "FROM customers c LEFT JOIN orders o " +
            "ON c.custcode = o.custcode " +
            "GROUP BY c.custname " +
            "ORDER BY countorders DESC", nativeQuery = true)
    List<OrderCounts> findOrderCount();
}
