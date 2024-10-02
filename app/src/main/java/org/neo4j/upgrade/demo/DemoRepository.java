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
        return driver.executableQuery("CALL custom.getLineStops($url)")
                .withParameters(Map.of("url", lineUri.toString()))
                .execute().records().stream()
                .map(this::stopMapper);
    }

    private Stop stopMapper(Record record) {
        return new Stop(
                record.get("name").asString(),
                record.get("url").asString(),
                record.get("connections").asList(Value::asString));
    }
}
