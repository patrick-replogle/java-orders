package com.patrick.replogle.javaorders.repositories;

import com.patrick.replogle.javaorders.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
}
