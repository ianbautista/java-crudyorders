package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Agent;

public interface AgentServices
{
    Agent save(Agent agent);

    Agent findByAgentcode(long agentcode);

}
