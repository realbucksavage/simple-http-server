package sarvika.simpleserver.serve;

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

    public RequestHandler(Socket clientSocket){
        this.socket = clientSocket;
        String request = getRequest(socket);

        RequestParser requestParser = new RequestParser(request);

        OutputStream socketOutputStream  = null;
        try {
            socketOutputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestedResource(socketOutputStream,requestParser.getresourcePath());

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getRequest(Socket socket){

        Scanner bufferedReader = null;
        try {
            bufferedReader = new Scanner(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
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
