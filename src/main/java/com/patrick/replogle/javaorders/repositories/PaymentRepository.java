package com.patrick.replogle.javaorders.repositories;

import com.patrick.replogle.javaorders.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long>
{
}
