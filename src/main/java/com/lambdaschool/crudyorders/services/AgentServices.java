package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;

import java.util.List;

public interface AgentServices
{
    Agent save(Agent agent);

    Agent findByAgentcode(long agentcode);

    void delete(long agentcode);

    List<Agent> findAllAgents();

}
