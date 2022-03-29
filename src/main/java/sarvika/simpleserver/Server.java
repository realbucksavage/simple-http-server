package sarvika.simpleserver;

import sarvika.simpleserver.serve.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server{

    private final ServerProperties properties;

    private ServerSocket serverSocket;

    public Server(ServerProperties properties) {
        this.properties = properties;
    }

    public void start() throws IllegalAccessException, IOException {
        // TODO: say what port server is running on
        // TODO: say what work directory server is using

        if (serverSocket != null && !serverSocket.isClosed()) {
            throw new IllegalAccessException("server is already running");
        }

        this.serverSocket = new ServerSocket(properties.getPort());
        while (!this.serverSocket.isClosed()) {
            final Socket client = serverSocket.accept();

            // TODO: debug logging - info about client
            new RequestHandler(client).start();
        }

        System.out.println("done");
    }

    public void stop() throws IOException {
        // TODO: say server is going to be shut down
        System.out.println("server closing");
        this.serverSocket.close();
    }
}
