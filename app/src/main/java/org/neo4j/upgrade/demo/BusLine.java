package org.neo4j.upgrade.demo;

import java.util.List;

public record BusLine(String id, String url, List<BusStop> stops) {
}
