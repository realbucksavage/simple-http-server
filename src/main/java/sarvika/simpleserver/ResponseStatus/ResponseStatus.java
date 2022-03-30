package sarvika.simpleserver.ResponseStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseStatus {

    private static Logger log = LogManager.getLogger(ResponseStatus.class.getName());

    public void sendOkResponse(OutputStream outStream, String contentType){
        log.info("Response Class has been initialzed");
        String status = "HTTP/1.1 200 OK\r\n"+"ContentType: "+contentType+"\r\n"+"\r\n";
        try {
            outStream.write(status.getBytes());
        } catch (IOException e) {
            log.debug("couldn't able to write into the outstream of client socket"+e);
            e.printStackTrace();
        }
    }

    public void sendNotFoundResponse(OutputStream outStream){
        log.info("Response Class has been initialzed");
        String status = "HTTP/1.1 404 Not Found\r\n"+"\r\n";
        try {
            outStream.write(status.getBytes());
        } catch (IOException e) {
            log.debug("couldn't able to write into the outstream of client socket"+e);
            e.printStackTrace();
        }
    }

}
