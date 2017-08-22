package com.falc.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;

    public Server() throws IOException {

    }
    public void createServerSoket(){
        try {
            server = new ServerSocket(Config.PORT);
        } catch (IOException e) {
            System.out.println("Ошибка создания сокета");
        }
        System.out.println("Сервер запущен");
    }
    public void startServer() throws IOException {
        try {
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
