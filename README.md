# Simple Axon Framework setup

This example shows how the Axon Framework can be used without external services.

The EventStore is embedded (`EmbeddedEventStore`) and the events are stored in memory (`InMemoryEventStorageEngine`). Commands and queries are dispatched directly using `SimpleCommandBus` and `SimpleQueryBus`.
See [MyAxonConfiguration](src/main/java/org/example/MyAxonConfiguration.java) for configuration details.

The events are also dispatched directly using `SimpleEventBus`. Therefor the dependency to `axon-server-connector` is excluded.

Start the application with `./gradlew bootRun` and test it using `curl -X PUT localhost:8080` to add data and `curl localhost:8080` to retrieve the amount of it.