package org.example.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.coreapi.CreateExampleCommand;
import org.example.coreapi.ExampleCreatedEvent;

import java.util.UUID;

@Aggregate
class Example {

    @AggregateIdentifier
    private UUID id;

    protected Example(){}

    @CommandHandler
    public Example(CreateExampleCommand createExampleCommand){
        AggregateLifecycle.apply(new ExampleCreatedEvent(createExampleCommand.getId()));
    }

    @EventSourcingHandler
    protected void on(ExampleCreatedEvent exampleCreatedEvent){
        this.id = exampleCreatedEvent.getId();
    }

}
