package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.views.OrderCounts;

import java.util.List;

public interface CustomerServices
{
   List<Customer> findAllCustomers();

   Customer findCustomerById(long id);

   List<Customer> findByLikeName(String subname);

   List<OrderCounts> findOrderCount();

   Customer save(Customer customer);

   void delete(long custid);

   Customer update(Customer customer, long custid);
}
