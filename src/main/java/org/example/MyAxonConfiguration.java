package org.example;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.queryhandling.*;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Simple Axon configuration:
 *
 *  Event Store -> EmbeddedEventStore and InMemoryEventStorageEngine.
 *  Command Bus -> SimpleCommandBus
 *  Query Bus -> SimpleQueryBus
 *  axon-server-connector excluded to use SimpleEventBus
 */
@Configuration
class MyAxonConfiguration {

    // The Event store `EmbeddedEventStore` delegates actual storage and retrieval of events to an `EventStorageEngine`.
    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    // The `InMemoryEventStorageEngine` stores each event in memory
    @Bean
    public EventStorageEngine storageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    public SimpleCommandBus commandBus(TransactionManager txManager, AxonConfiguration axonConfiguration) {
        SimpleCommandBus commandBus =
                SimpleCommandBus.builder()
                        .transactionManager(txManager)
                        .messageMonitor(axonConfiguration.messageMonitor(CommandBus.class, "commandBus"))
                        .build();
        commandBus.registerHandlerInterceptor(
                new CorrelationDataInterceptor<>(axonConfiguration.correlationDataProviders())
        );
        return commandBus;
    }

    @Bean
    public SimpleQueryBus queryBus(AxonConfiguration axonConfiguration, TransactionManager transactionManager) {
        return SimpleQueryBus.builder()
                .messageMonitor(axonConfiguration.messageMonitor(QueryBus.class, "queryBus"))
                .transactionManager(transactionManager)
                .errorHandler(axonConfiguration.getComponent(
                        QueryInvocationErrorHandler.class,
                        () -> LoggingQueryInvocationErrorHandler.builder().build()
                ))
                .queryUpdateEmitter(axonConfiguration.getComponent(QueryUpdateEmitter.class))
                .build();
    }
}
