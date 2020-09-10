package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController
{
    @Autowired
    AgentServices agentServices;

    // http://localhost:2019/agents/all
    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAllAgents()
    {
        List<Agent> agentList = agentServices.findAllAgents();
        return new ResponseEntity<>(agentList,HttpStatus.OK);
    }

    // http://localhost:2019/agents/agent/9
    @GetMapping(value = "/agent/{agentcode}", produces = {"application/json"})
    public ResponseEntity<?> findAgentByAgentcode(@PathVariable long agentcode)
    {
        Agent agent = agentServices.findByAgentcode(agentcode);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }

    // STRETCH
    // DELETE /agents/unassigned/{agentcode} - Deletes an agent if they are not assigned to a customer
    // DELETE http://localhost:2019/agents/unassigned/8
    // DELETE http://localhost:2019/agents/unassigned/16
    @DeleteMapping(value = "/unassigned/{agentcode}")
    public ResponseEntity<?> deleteUnassignedAgent(@PathVariable long agentcode)
    {
        agentServices.delete(agentcode);
        return new ResponseEntity<>("No Body Data", HttpStatus.OK);
    }

}
