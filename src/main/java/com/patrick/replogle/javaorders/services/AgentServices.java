package com.patrick.replogle.javaorders.services;

import com.patrick.replogle.javaorders.models.Agent;

import java.util.List;

public interface AgentServices
{
    List<Agent> findAllAgents();

    Agent findAgentById(long id);

    Agent save(Agent agent);
}
