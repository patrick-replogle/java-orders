package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.repositories.CustomerRepository;
import com.patrick.replogle.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    CustomerRepository custrepos;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> myList = new ArrayList<>();

        custrepos.findAll().iterator().forEachRemaining(myList::add);

        return myList;
    }

    @Override
    public Customer findCustomerById(long id)
    {
        return custrepos.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
    }

    @Override
    public List<Customer> findByLikeName(String subname)
    {
        List<Customer> list = custrepos.findByCustnameContainingIgnoringCase(subname);

        return list;
    }

    @Override
    public List<OrderCounts> findOrderCount()
    {
        List<OrderCounts> list = custrepos.findOrderCount();

        return list;
    }

    @Override
    public Customer save(Customer customer)
    {
        return custrepos.save(customer);
    }

    @Transactional
    @Override
    public void delete(long custid)
    {
        if (custrepos.findById(custid).isPresent())
        {
            custrepos.deleteById(custid);
        } else
        {
            throw new EntityNotFoundException("Customer " + custid + " not found!");
        }
    }
}
