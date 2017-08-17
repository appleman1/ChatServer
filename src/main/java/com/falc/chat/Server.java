package com.falc.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;

    public Server() throws IOException {
        try {
            server = new ServerSocket(Config.PORT);
            System.out.println("Сервер запущен");

            while (true) {
                Socket socket = server.accept();

                Connection con = new Connection(socket);
                Connection.getConnections().add(con);
                con.start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }
}
