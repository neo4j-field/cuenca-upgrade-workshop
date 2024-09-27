LOAD CSV WITH HEADERS FROM 'file:///stops.csv' AS row
CALL {
    WITH row    

    MERGE (stop:BusStop {
        url: row.url
    })
    ON CREATE
        SET stop.stopName = row.stopName, 
            stop.railConnections = row.railConnections

    WITH row, stop
    MATCH (line:BusLine {url: row.lineUrl})
    MERGE (line)-[r:HAS_STOP {index: toInteger(row.index)}]->(stop)
    SET r.stopLetterCode = row.stopLetterCode

    RETURN stop
} IN TRANSACTIONS OF 1000 ROWS
RETURN COUNT (stop) as stops