package sarvika.simpleserver.serve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestParser {

    private final static Logger requestParserLogger = LogManager.getLogger(RequestParser.class.getName());

    private String resourcePath;
    private String method;

    public RequestParser(String request) {

        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        this.method = requestLine[0];
        requestParserLogger.debug("Method: "+this.method);
        this.resourcePath = requestLine[1];
        requestParserLogger.debug("Resource Path: "+resourcePath);

    }

    public String getresourcePath() {
        return resourcePath;
    }

    public String getMethod() {
        return method;
    }
}


