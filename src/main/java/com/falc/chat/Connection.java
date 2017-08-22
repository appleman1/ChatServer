package com.falc.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private String name = "";
    private Socket socket;
    private static ArrayList<Connection> connections = new ArrayList<>();
    public static ArrayList<Connection> getConnections() {
        return connections;
    }
    public Connection(Socket socket){
        this.socket = socket;
    }
    public void run() {
        try {

            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            name = in.readLine();

            for (Connection c : connections) {
                c.out.println(name + " присоединился к чату");
            }
            String str = "";
            while (true) {
                str = in.readLine();
                if (str.equals("exit")) break;

                for (Connection c : connections) {
                    c.out.println(name + ": " + str);
                }
            }

            for (Connection c : connections) {
                c.out.println(name + " покинул чат.");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*try {
                *//*in.close();
                out.close();*//*

            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
