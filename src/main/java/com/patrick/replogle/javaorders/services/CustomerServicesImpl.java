package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Agent;
import com.patrick.replogle.javaorders.models.Customer;
import com.patrick.replogle.javaorders.models.Order;
import com.patrick.replogle.javaorders.repositories.AgentRepository;
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

    @Autowired
    AgentRepository agentrepos;

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

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0)
        {
            findCustomerById(customer.getCustcode());
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        // agent
        Agent agent = agentrepos.findById(customer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentname() + " not found!"));
        newCustomer.setAgent(agent);

        // orders
        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders())
        {
            Order newOrder = new Order(
                    o.getOrdamount(),
                    o.getAdvanceamount(),
                    newCustomer,
                    o.getOrderdescription());

            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
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

    @Transactional
    @Override
    public Customer update(Customer customer, long custid)
    {
        Customer updateCustomer = findCustomerById(custid);

        //Agent agent

        if (customer.getCustname() != null)
        {
            updateCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null)
        {
            updateCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null)
        {
            updateCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null)
        {
            updateCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade() != null)
        {
            updateCustomer.setGrade(customer.getGrade());
        }

        if (customer.getPhone() != null)
        {
            updateCustomer.setPhone(customer.getPhone());
        }

        if (customer.getAgent() != null)
        {
            updateCustomer.setAgent(customer.getAgent());
        }

        if (customer.hasvalueforopeningamt)
        {
            updateCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasvalueforpaymentamt)
        {
            updateCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.hasvalueforoutstandingamt)
        {
            updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.hasvalueforreceiveamt)
        {
            updateCustomer.setReceiveamt(customer.getReceiveamt());
        }
        // agent
        if (customer.getAgent() != null)
        {
            Agent agent = agentrepos.findById(customer.getAgent().getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentname() + " not found!"));
            updateCustomer.setAgent(agent);
        }

        // orders
        if (customer.getOrders().size() > 0)
        {
            updateCustomer.getOrders().clear();

            for (Order o : customer.getOrders())
            {
                Order newOrder = new Order(
                        o.getOrdamount(),
                        o.getAdvanceamount(),
                        updateCustomer,
                        o.getOrderdescription());

                updateCustomer.getOrders().add(newOrder);
            }
        }


        return custrepos.save(updateCustomer);
    }
}
