import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Serialize {
    byte[] serialize(Message mes) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        if (mes == null) return null;
        oos.writeObject(mes);
        byte[] buf = baos.toByteArray();
        oos.close();
        baos.close();
        return buf;
    }

    public Message deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Message mes = (Message) ois.readObject();
        bais.close();
        ois.close();
        return mes;
    }

    public InetSocketAddress getAddressFromMessage(String addr) throws UnknownHostException {
        int pos = addr.indexOf("/");
        String ip = addr.substring(0, pos);
        String port = addr.substring(pos + 1);
        return new InetSocketAddress(InetAddress.getByName(ip), Integer.parseInt(port));
    }


}

