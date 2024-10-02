package org.neo4j;


import org.neo4j.graphdb.*;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;
import org.neo4j.procedure.Description;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    @Context
    public Transaction tx;

    @Context
    public Log log;


    @Procedure(name = "custom.getLineStops", mode = Mode.READ)
    @Description("Get the bus stops on the given line.")
    public Stream<Stop> getLineStops(@Name("lineUrl") String url) {
        log.infoLogger().log("call to getLineStops with argument: "+url);
        Node line;
        try{
            line = tx.findNode(Label.label("BusLine"), "url", url);
        } catch (MultipleFoundException e){
            log.errorLogger().log("Too many lines found");
            return Stream.empty();
        }
        if (line == null) {
            log.errorLogger().log("Line not found");
            return Stream.empty();
        }
        List<Node> stops = new ArrayList<>();
        Iterable<Relationship> relationships = line.getRelationships(Direction.OUTGOING, RelationshipType.withName("HAS_STOP"));
        relationships.iterator()
                .forEachRemaining(rel -> stops.add(rel.getEndNode()));

        log.infoLogger().log("call to getLineStops succeeded");
        return stops.stream().map(n -> new Stop((String)n.getProperty("url"),
                (String)n.getProperty("stopName", "N/A"),
                (String)n.getProperty("railConnections", "none")));
    }

    public static class Stop {
        public String name;
        public String url;
        public List<String> connections;

        public Stop(String url, String name, String connections) {
            this.url = url;
            this.name = name;
            this.connections = Arrays.stream(connections.split(","))
                    .filter(s -> !"none".equals(s))
                    .collect(Collectors.toList());
        }

    }

}