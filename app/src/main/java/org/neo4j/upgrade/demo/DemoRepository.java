package org.neo4j.upgrade.demo;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.Map;
import java.util.stream.Stream;

@Repository
class DemoRepository {

    private final Driver driver;

    DemoRepository(Driver driver) {
        this.driver = driver;
    }

    Stream<Stop> getLineStops(URI lineUri) {
        return driver.session().readTransaction(tx ->
                tx.run("CALL custom.getLineStops($url)", Map.of("url", lineUri.toString()))
                        .stream()
                        .map(this::stopMapper)
                        .toList()
                        .stream()
        );
    }

    private Stop stopMapper(Record record) {
        return new Stop(
                record.get("name").asString(),
                record.get("url").asString(),
                record.get("connections").asList(Value::asString));
    }
}
