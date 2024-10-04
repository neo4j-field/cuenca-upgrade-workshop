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
    public Stream<BusLine> index() {
        return demoRepository.listAllLinesWithFirstStop();
    }

    @GetMapping("/bus/route/{routeId}/stops")
    public Stream<BusStop> foo(@PathVariable int routeId, @RequestParam String direction) {
        URI lineUri = URI.create("https://tfl.gov.uk/bus/route/" + routeId + "?direction=" + direction);
        return demoRepository.getLineStops(lineUri);
    }
}
