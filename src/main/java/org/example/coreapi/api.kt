package org.example.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

class CreateExampleCommand {
    var id: UUID = UUID.randomUUID()
}

data class ExampleCreatedEvent(@TargetAggregateIdentifier val id: UUID)

class ExampleQuery