package com.lambdaschool.ordersapp.repositories;

import com.lambdaschool.ordersapp.models.Agent;
import com.lambdaschool.ordersapp.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface AgentsRepository extends CrudRepository<Agent, Long>
{
    Agent findByAgentcode(long agentcode);

}
