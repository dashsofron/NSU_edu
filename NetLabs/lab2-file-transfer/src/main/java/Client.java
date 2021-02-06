import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    //filePath<=4096 byte;+
    //file<=1TB+
    File file;
    InetAddress serverIP;
    Socket socket;

    Client(String filePath, String host, int port) throws IOException {
        Path path = Paths.get(filePath);

        file = new File(filePath);
        if (file.getName().length() * 2 > 4096) System.out.println("too long name for file");
        if (fileLenhthInGb(file) > 1024) System.out.println("too large file");
        socket = new Socket(host, port);

    }

    long fileLenhthInGb(File file) {
        return file.length() / (1024 * 1024 * 1024);
    }

    public static void main(String[] args) throws IOException {
        //String filePath = "Logging with Logcat.mp4";
        String filePath=args[0];
        String serverIP = "0.0.0.0";
        int port = 8081;
        Client client = new Client(filePath, serverIP, port);
        client.socket.setSoTimeout(0);
        InputStream sin = client.socket.getInputStream();
        OutputStream sout = client.socket.getOutputStream();
        DataInputStream dis = new DataInputStream(sin);
        DataOutputStream dos = new DataOutputStream(sout);

        client.sendFile(dos);
        Boolean success = client.getSuccessInformation(dis);
        if (success) System.out.println("successful sending");
        else System.out.println("Something goes wrong with sending");
        client.socket.close();
    }

    //UTF-8, TCP+
    //send name, size and file+
    //вывод сообщения об успехе+
    void sendFile(DataOutputStream dos) throws IOException {
        byte[] buf = new byte[256];
        System.out.println("********* sending: " + file.getName() + " *********");
        dos.writeLong(file.getName().length());
        dos.flush();
        dos.writeUTF(file.getName());
        dos.flush();
        dos.writeLong(file.length());
        dos.flush();
        FileInputStream reader = new FileInputStream(file);
        //FileReader reader = new FileReader(file);
        long all = 0;
        long perc = file.length() / 100;

        int n = reader.read(buf);
        if(n<256)buf=new byte[n];
        while (n > 0) {
            all += n;
            //dos.writeLong(n);
            dos.write(buf);
            //dos.writeUTF(String.valueOf(buf));
            buf = new byte[256];
            n = reader.read(buf);
            if(n<=0)continue;
            if(n<256)buf=new byte[n];
        }
        dos.flush();
    }

    Boolean getSuccessInformation(DataInputStream dis) throws IOException {
        return dis.readBoolean();
    }
}
