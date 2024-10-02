package org.neo4j.upgrade.demo;

import java.util.List;

public record Stop(String name, String url, List<String> connections) {
}
