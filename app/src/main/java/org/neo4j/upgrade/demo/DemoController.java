package org.neo4j.upgrade.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.stream.Stream;

@RestController
public class DemoController {

    private final DemoRepository demoRepository;

    public DemoController(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/bus/route/{routeId}/stops")
    public Stream<Stop> foo(@PathVariable int routeId, @RequestParam String direction) {
        URI lineUri = URI.create("https://tfl.gov.uk/bus/route/" + routeId + "?direction=" + direction);
        return demoRepository.getLineStops(lineUri);
    }
}
