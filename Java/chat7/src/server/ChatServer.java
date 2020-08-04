package server;

import network.TCPConnection;
import network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {

    private Thread thread;
    public int NUM;

    private final static ArrayList<TCPConnection> connections = new ArrayList<>();

    public ChatServer() {
        System.out.println("Server running");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket sSocket = new ServerSocket(8189)) {
                    while (true) {
                        try {
                                new TCPConnection(sSocket.accept(), ChatServer.this);
                            //мб это вызывает конструктор не тот для клиента?
                        } catch (IOException e) {
                            System.out.println("TCP connection exception " + e);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        });
        thread.start();
    }

    @Override
    public synchronized void ConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendData("client" + connections.size() + "  connected: " + tcpConnection);
    }

    @Override
    public synchronized void ReceiveMessage(TCPConnection tcpConnection, String message) {
        sendData(/*tcpConnection.name + " : " + */message);

    }

    @Override
    public synchronized void Disconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendData(/*tcpConnection.name + */" disconnected " + tcpConnection);

    }

    @Override
    public synchronized void Exception(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCP connection exception: " + e);
    }

    private void sendData(String message) {
        if (message != null) System.out.println(message);
        for (TCPConnection connection : connections) connection.sendMessage(message);
    }

    public int getConNum() {
        return connections.size();
    }

}
