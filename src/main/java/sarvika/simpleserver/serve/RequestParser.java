package sarvika.simpleserver.serve;

public class RequestParser {

    private String resourcePath;
    private String method;

    public RequestParser(String request) {

        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        this.method = requestLine[0];
        this.resourcePath = requestLine[1];

    }

    public String getresourcePath() {
        return resourcePath;
    }

    public String getMethod() {
        return method;
    }
}


