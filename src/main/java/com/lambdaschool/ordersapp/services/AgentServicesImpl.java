package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Agent;
import com.lambdaschool.ordersapp.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    AgentsRepository agentrepos;

    @Override
    public Agent save(Agent agent)
    {
        return agentrepos.save(agent);
    }

    @Override
    public Agent findByAgentcode(long agentcode)
    {
        Agent agent = agentrepos.findByAgentcode(agentcode);
        return agent;
    }
}
