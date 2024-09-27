package org.neo4j;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;
import org.neo4j.procedure.Description;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    @Context
    public Transaction tx;

    @Context
    public Log log;


    @Procedure(name = "custom.getRelationshipTypes", mode = Mode.READ)
    @Description("Get the different relationships going in and out of a node.")
    public Stream<RelationshipTypes> getRelationshipTypes(@Name("node") Node node) {
        List<String> outgoing = new ArrayList<>();
        node.getRelationships(Direction.OUTGOING).iterator()
                .forEachRemaining(rel -> outgoing.add(rel.getType().toString()));

        List<String> incoming = new ArrayList<>();
        Iterable<Relationship> relationships = node.getRelationships(Direction.INCOMING);

        relationships.iterator().forEachRemaining(rel -> incoming.add(rel.getType().toString()));

        log.infoLogger().log("call to getRelationshipTypes succeeded");
        return Stream.of(new RelationshipTypes( List.copyOf(new HashSet<String>(incoming)),
                                                List.copyOf(new HashSet<String>(outgoing))
                 )
        );
    }


    public static class RelationshipTypes {
        // These records contain two lists of distinct relationship types going in and out of a Node.
        public List<String> outgoing;
        public List<String> incoming;

        public RelationshipTypes(List<String> incoming, List<String> outgoing) {
            this.outgoing = outgoing;
            this.incoming = incoming;
        }
    }

}