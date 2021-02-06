import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    ServerSocket sSocket;
    List<ConnectionThread> connections;

    Server(int port) throws IOException {
        sSocket = new ServerSocket(port);

        System.out.println(sSocket.getInetAddress());
        //connections=new ArrayList<ConnectionThread>();
    }

    //save to uploads with original name+
    //check speed every 3 seconds(instant and average)+
    //отдельно для каждого клиента+
    //если активен менее 3 секунд - скорость за меньшеевремя (пока был активен)+
    //проверка совпадения размеров и сообщение об успехе неуспехе, потом разрыв+
    //рботать параллельно+
    //в случае ошибки - разрыв с ошибочным клиентом+
    public static void main(String[] args) throws IOException {
        int port = 8081;
        Server server = new Server(port);
        server.run();
    }

    void run() throws IOException {
        while (true) {
            Socket socket = sSocket.accept();
            startConnection(socket);
        }
    }

    void startConnection(Socket socket) throws IOException {
        Thread newThread = new Thread(new ConnectionThread(socket));
        //connections.add(newThread);
        newThread.start();
    }


}
