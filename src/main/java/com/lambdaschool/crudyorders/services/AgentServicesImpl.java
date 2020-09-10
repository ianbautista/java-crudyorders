package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    AgentsRepository agentrepos;

    @Autowired
    CustomersRepository custrepos;

    @Override
    public List<Agent> findAllAgents()
    {
        List<Agent> list = new ArrayList<>();
        agentrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

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

    @Transactional
    @Override
    public void delete(long agentcode)
    {
        if(agentrepos.findById(agentcode).isPresent())
        {
            List<Customer> customerList = agentrepos.findById(agentcode).get().getCustomers();
            if(customerList.isEmpty())
            {
                agentrepos.deleteById(agentcode);
            } else
            {
                throw new EntityNotFoundException("Found A Customer For Agent " + agentcode);
            }
        } else
        {
            throw new EntityNotFoundException("Agent " + agentcode + " Not Found!");
        }
    }
}
