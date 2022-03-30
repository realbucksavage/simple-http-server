package sarvika.simpleserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sarvika.simpleserver.serve.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server{

    private final static Logger serverLogger = LogManager.getLogger(Server.class.getName());

    private final ServerProperties properties;

    private ServerSocket serverSocket;



    public Server(ServerProperties properties) {
        this.properties = properties;
    }

    public void start() throws IllegalAccessException, IOException {
        serverLogger.info("Starting Server ....");
        // TODO: say what port server is running on
        // TODO: say what work directory server is using
        serverLogger.debug("Server getting Port: "+properties.getPort());
        serverLogger.debug("Server use Working directory: "+properties.getWorkingDir());

        if (serverSocket != null && !serverSocket.isClosed()) {
            serverLogger.error("Caution: Server is Already Running");
            throw new IllegalAccessException("server is already running");
        }

        this.serverSocket = new ServerSocket(properties.getPort());

        while (!this.serverSocket.isClosed()) {

            final Socket client = serverSocket.accept();

            // TODO: debug logging - info about client
            serverLogger.info("Client Accepted");
            serverLogger.info("Client :"+client.toString());

            new RequestHandler(client).start();

        }

        System.out.println("done");
    }

    public void stop() throws IOException {
        // TODO: say server is going to be shut down
        serverLogger.info("Server is going to be shut down");
        this.serverSocket.close();
    }
}
