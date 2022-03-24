package com.httpServer;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable{

    public static ArrayList<RequestHandler> clients = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader ;

    public RequestHandler(Socket socket) {
        try{
            this.socket = socket;
//            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clients.add(this);
        }catch(IOException e){
            closeEverything(socket, bufferedReader);
        }
    }
    private void closeEverything(Socket socket, BufferedReader bufferedReader) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientHandler() {
        clients.remove(this);
    }

    @Override
    public void run() {
       handleRequest(socket);
    }

    private void handleRequest(Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("I am in  new Thread");
                    StringBuilder requestBuilder = new StringBuilder();
                    String line;
                    while (!(line = bufferedReader.readLine()).isBlank()) {
                        requestBuilder.append(line + "\r\n");
                    }
                    String request = requestBuilder.toString();
                    System.out.println(request);

                    String[] requestsLines = request.split("\r\n");
                    String[] requestLine = requestsLines[0].split(" ");
                    String method = requestLine[0];
                    String path = requestLine[1];
                    String version = requestLine[2];
                    String host = requestsLines[1].split(" ")[1];

                    List<String> headers = new ArrayList<>();
                    for (int h = 2; h < requestsLines.length; h++) {
                        String header = requestsLines[h];
                        headers.add(header);
                    }
                    String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                            socket.toString(), method, path, version, host, headers.toString());
                    System.out.println(accessLog);


                }catch(Exception e){
                    System.out.println(e.getMessage());
                }

            }
        }).start();
    }

//    private void sendResponse(Socket socket, String status, String contentType, byte[] content) throws IOException {
//        System.out.println("I am in sendResponse");
//        OutputStream clientOutput = socket.getOutputStream();
////        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
//        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
////        clientOutput.write("\r\n".getBytes());
////        clientOutput.write(content);
////        clientOutput.write("\r\n\r\n".getBytes());
//        clientOutput.flush();
//        socket.close();
//    }

//    private String guessContentType(Path filePath) throws IOException {
//        System.out.println("I am in guessContentType");
//        return Files.probeContentType(filePath);
//    }

//    private Path getFilePath(String path) {
//        System.out.println("I am in getFilePath method");
//        if ("/".equals(path)) {
//            path = "/index.html";
//        }
//        return Paths.get("/tmp/www", path);
//    }

}
