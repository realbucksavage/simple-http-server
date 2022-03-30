package sarvika.simpleserver.serve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sarvika.simpleserver.Main;
import sarvika.simpleserver.ResourceHandling.ResourceHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public final class RequestHandler extends Thread{

    private String resource;
    private String method;
    private Map<String, String[]> headers;
    private final Socket socket;

    private final static Logger requestHandlerLogger = LogManager.getLogger(RequestHandler.class.getName());

    public RequestHandler(Socket clientSocket){
        requestHandlerLogger.info("Client Accepted");
        this.socket = clientSocket;
        requestHandlerLogger.debug("client socket: "+this.socket);
        String request = getRequest(socket);
        requestHandlerLogger.debug("Client Request: "+request);

        RequestParser requestParser = new RequestParser(request);

        OutputStream socketOutputStream  = null;
        try {
            socketOutputStream = socket.getOutputStream();
        } catch (IOException e) {
            requestHandlerLogger.fatal("SocketOutputStream Exception: "+e.getMessage());
            e.printStackTrace();
        }

        requestedResource(socketOutputStream,requestParser.getresourcePath());

        try {
            socket.close();
            requestHandlerLogger.info("Socket: "+socket.toString()+" close.");
        } catch (IOException e) {
            requestHandlerLogger.fatal("Encounter an Exception while closing the socket: "+e.getMessage());
            e.printStackTrace();
        }

    }
    public String getRequest(Socket socket){
        requestHandlerLogger.info("Getting Request....");
        Scanner bufferedReader = null;
        try {
            bufferedReader = new Scanner(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            requestHandlerLogger.fatal("Exception while getting Request:"+e.getMessage());
            e.printStackTrace();
        }
        StringBuilder requestBuilder = new StringBuilder();


        String line;
        while (!(line = bufferedReader.nextLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }
        return requestBuilder.toString();

   }

    public void requestedResource(OutputStream clientOut, String filePath){
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.getMeThisResource(clientOut,filePath);
    }


}
