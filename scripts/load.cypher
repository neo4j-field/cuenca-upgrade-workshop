// schema
CREATE CONSTRAINT line_url IF NOT EXISTS ON (l:BusLine) ASSERT l.url IS NODE KEY;
CREATE CONSTRAINT stop_url IF NOT EXISTS ON (s:BusStop) ASSERT s.url IS NODE KEY;

//load lines
LOAD CSV WITH HEADERS FROM 'file:///bus_lines.csv' AS row
MERGE (line:BusLine { url: row.url })
ON CREATE
    SET line.lineNo = row.lineNo,
        line.direction = row.direction,
        line.from = row.from,
        line.to = row.to;

//load stops in batches
LOAD CSV WITH HEADERS FROM 'file:///stops.csv' AS row
CALL {
    WITH row    
    MERGE (stop:BusStop { url: row.url })
    ON CREATE
        SET stop.stopName = row.stopName, 
            stop.railConnections = row.railConnections

    WITH row, stop
    MATCH (line:BusLine {url: row.lineUrl})
    MERGE (line)-[r:HAS_STOP {index: toInteger(row.index)}]->(stop)
    ON CREATE
        SET r.stopLetterCode = row.stopLetterCode
    RETURN stop
}
RETURN count(stop) as stops;

//model enrichment
MATCH (prev:BusStop)<-[prev_r:HAS_STOP]-(l:BusLine)-[next_r:HAS_STOP]->(next:BusStop) 
    WHERE next_r.index = prev_r.index + 1
    AND EXISTS(l.direction)
MERGE (prev)-[:NEXT_STOP {line:l.lineNo, direction: l.direction}]->(next);