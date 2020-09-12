package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Agent;
import com.patrick.replogle.javaorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "agentService")
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    AgentRepository agentrepos;

    @Override
    public List<Agent> findAllAgents()
    {
        List<Agent> agents = new ArrayList<>();
        agentrepos.findAll().iterator().forEachRemaining(agents::add);

        return agents;
    }

    @Override
    public Agent findAgentById(long id)
    {
        return agentrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found."));
    }

    @Override
    public Agent save(Agent agent)
    {
        return agentrepos.save(agent);
    }
}
