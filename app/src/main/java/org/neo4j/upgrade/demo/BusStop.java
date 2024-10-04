package org.neo4j.upgrade.demo;

import java.util.List;

public record BusStop(String name, List<String> connections) {
}
