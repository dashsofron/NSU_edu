package network;

import server.ChatServer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Objects;

public class TCPConnection {
    private final Socket socket;
    private final Thread thread;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final TCPConnectionListener listener;
    public  final String name;
    int conNum;
    Boolean serv=false;

    public TCPConnection(Socket socket, TCPConnectionListener listener, String nick) throws IOException {
        this.socket = socket;
        this.listener = listener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        conNum=listener.getConNum();
        name=nick;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listener.ConnectionReady(TCPConnection.this);
                    System.out.println("name ="+name);

                    while (!thread.isInterrupted())
                    { listener.ReceiveMessage(TCPConnection.this, in.readLine());
                    System.out.println("name ="+name);}

                } catch (IOException e) {
                    listener.Exception(TCPConnection.this,e);
                } finally {
                    listener.Disconnect(TCPConnection.this);
                }
            }
        });
        thread.start();
    }

    public TCPConnection(Socket socket, TCPConnectionListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        conNum=listener.getConNum()+1;
        name = "user"+conNum;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listener.ConnectionReady(TCPConnection.this);
                    System.out.println("name ="+name);

                    while (!thread.isInterrupted())
                    { listener.ReceiveMessage(TCPConnection.this, in.readLine());
                        System.out.println("name ="+name);}

                } catch (IOException e) {
                    listener.Exception(TCPConnection.this,e);
                } finally {
                    listener.Disconnect(TCPConnection.this);
                }
            }
        });
        thread.start();
    }

    public TCPConnection (TCPConnectionListener listener, String ipAddr, int port,Boolean s) throws IOException{
        this(new Socket(ipAddr,port),listener);
    }
    public TCPConnection (TCPConnectionListener listener, String ipAddr, int port, String nick) throws IOException{
        this(new Socket(ipAddr,port),listener,nick);
    }
    public synchronized void sendMessage(String message) {
        try {
            if(!serv)out.write(name+ "-" +message+ "\r\n");
            else  out.write(message+ "\r\n");
            out.flush();
        } catch (IOException e) {
            listener.Exception(TCPConnection.this,e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.Exception(TCPConnection.this,e);
        }
    }
    public  String toString() {
        return  "TCPConnection: " +socket.getInetAddress() + ": " + socket.getPort();
    }
    public int getConNum(){
        return listener.getConNum();
    }

}

