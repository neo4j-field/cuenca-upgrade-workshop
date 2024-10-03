package org.neo4j.upgrade.demo;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
class DemoRepository {

    private final Driver driver;

    DemoRepository(Driver driver) {
        this.driver = driver;
    }

    Stream<BusLine> listAllLinesWithFirstStop() {
        return driver.session().readTransaction(tx ->
                tx.run("""
                                MATCH (stop:BusStop) WHERE NOT ()-[:NEXT_STOP]->(stop)
                                CALL {
                                    WITH stop
                                    RETURN (stop)<-[:HAS_STOP]-() as p
                                }
                                WITH stop, nodes(p[0])[1] as line // why not? YOLO! 🤘
                                RETURN stop.stopName as name, null as url, split(stop.railConnections, ',') as connections
                                       , line.lineNo as lineNo, line.url as lineUrl
                                """)
                        .stream()
                        .map(record -> {
                            BusStop stop = stopMapper(record);
                            return new BusLine(record.get("lineNo").asString(), record.get("lineUrl").asString(), List.of(stop));
                        })
                        .toList()
                        .stream()
        );
    }

    Stream<BusStop> getLineStops(URI lineUri) {
        return driver.session().readTransaction(tx ->
                tx.run("CALL custom.getLineStops($url)", Map.of("url", lineUri.toString()))
                        .stream()
                        .map(this::stopMapper)
                        .toList()
                        .stream()
        );
    }

    private BusStop stopMapper(Record record) {
        return new BusStop(
                record.get("name").asString(),
                record.get("connections").asList(Value::asString, List.of()));
    }
}
