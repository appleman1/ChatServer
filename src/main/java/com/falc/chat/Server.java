package com.falc.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket server;
    private ExecutorService service = Executors.newCachedThreadPool();
    private ConcurrentHashMap<String, PrintWriter> mapClients = new ConcurrentHashMap<>();


    public Server() throws IOException {

    }

    public void createServerSoket() {
        try {
            server = new ServerSocket(Config.PORT);
            System.out.println("Сервер запущен");
        } catch (IOException e) {
            System.out.println("Ошибка создания сокета");
        }

    }

    public void startServer() throws IOException {
        try {
            while (true) {
                Socket socket = server.accept();
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket thisSoket = socket;
                            BufferedReader in = new BufferedReader(new InputStreamReader(thisSoket.getInputStream()));
                            PrintWriter out = new PrintWriter(thisSoket.getOutputStream(), true);

                            String name = in.readLine();
                            mapClients.putIfAbsent(name, out);


                            for (Map.Entry<String, PrintWriter> pair : mapClients.entrySet()) {
                                pair.getValue().println(name + " присоединился к чату");
                            }
                            String str = "";
                            while (true) {
                                str = in.readLine();
                                if (str.equals("exit")) break;

                                for (Map.Entry<String, PrintWriter> pair : mapClients.entrySet()) {
                                    pair.getValue().println(name + ": " + str);
                                }
                            }

                            for (Map.Entry<String, PrintWriter> pair : mapClients.entrySet()) {
                                pair.getValue().println(name + " покинул чат.");
                                mapClients.remove(name);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } finally {
            server.close();
            service.shutdown();
        }
    }
}
