package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Agent;
import com.patrick.replogle.javaorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "agentService")
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    AgentRepository agenstrepos;

    @Override
    public Agent save(Agent agent)
    {
        return agenstrepos.save(agent);
    }
}
