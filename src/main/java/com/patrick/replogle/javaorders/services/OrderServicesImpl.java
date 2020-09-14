package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.models.Order;
import com.patrick.replogle.javaorders.models.Payment;
import com.patrick.replogle.javaorders.repositories.CustomerRepository;
import com.patrick.replogle.javaorders.repositories.OrderRepository;
import com.patrick.replogle.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrderRepository ordersrepos;

    @Autowired
    CustomerRepository custrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public Order findOrderById(long id)
    {
        return ordersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
    }

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            findOrderById(order.getOrdnum());
            newOrder.setOrdnum(order.getOrdnum());
        }


//        Customer customer,
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        Customer customer = custrepos.findById(order.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustname() + " not found!"));
        newOrder.setCustomer(customer);

        // payments
        newOrder.getPayments().clear();
        for (Payment p : order.getPayments())
        {
            Payment newPayment = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

            newOrder.getPayments().add(newPayment);
        }

        return ordersrepos.save(newOrder);
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

    @Transactional
    @Override
    public Order update(Order order, long id)
    {
       Order updateOrder = findOrderById(id);

       if (order.hasvalueforordamount)
       {
           updateOrder.setOrdamount(order.getOrdamount());
       }

       if (order.hasvalueforadvancement)
       {
           updateOrder.setAdvanceamount(order.getAdvanceamount());
       }

       if (order.getOrderdescription() != null)
       {
           updateOrder.setOrderdescription(order.getOrderdescription());
       }

       if (order.getCustomer() != null)
        {
            Customer customer = custrepos.findById(order.getCustomer().getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustname() + " not found!"));
            updateOrder.setCustomer(customer);
        }

       if (order.getPayments().size() > 0)
       {
           updateOrder.getPayments().clear();

           for (Payment p : order.getPayments())
           {
               Payment newPayment = paymentrepos.findById(p.getPaymentid())
                       .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

               updateOrder.getPayments().add(newPayment);
           }
       }

       return ordersrepos.save(updateOrder);
    }
}
