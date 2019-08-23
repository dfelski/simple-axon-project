package org.example.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.coreapi.ExampleCreatedEvent;
import org.example.coreapi.ExampleQuery;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
class ExampleStatusProjection {

    private Set<UUID> examples = new HashSet<>();

    @EventHandler
    protected void on(ExampleCreatedEvent exampleCreatedEvent) {
        examples.add(exampleCreatedEvent.getId());
    }

    @QueryHandler
    int on(ExampleQuery exampleQuery) {
        return examples.size();
    }

}
