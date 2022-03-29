package sarvika.simpleserver.ResponseStatus;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseStatus {

    public void sendOkResponse(OutputStream outStream, String contentType) throws IOException {
        String status = "HTTP/1.1 200 OK\r\n"+"ContentType: "+contentType+"\r\n"+"\r\n";
        outStream.write(status.getBytes());
    }

    public void sendNotFoundResponse(OutputStream outStream) throws IOException {
        String status = "HTTP/1.1 200 OK\r\n"+"\r\n";
        outStream.write(status.getBytes());
    }

}
