package com.patrick.replogle.javaorders.controllers;

import com.patrick.replogle.javaorders.models.Agent;
import com.patrick.replogle.javaorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController
{
    @Autowired
    AgentServices agentServices;

    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> listAllAgents()
    {
        List<Agent> agents = agentServices.findAllAgents();

        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> findAgentById(@PathVariable long id)
    {
        Agent a = agentServices.findAgentById(id);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
