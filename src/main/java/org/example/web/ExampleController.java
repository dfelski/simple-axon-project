package org.example.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.example.coreapi.CreateExampleCommand;
import org.example.coreapi.ExampleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExampleController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private CommandGateway commandGateway;

    @GetMapping("/")
    Integer get() throws Exception{
        return queryGateway.query(new ExampleQuery(), Integer.class).get();
    }

    @PutMapping("/")
    void add() throws Exception{
        commandGateway.send(new CreateExampleCommand());
    }
}
