import java.io.IOException;
import java.net.MulticastSocket;
import java.net.*;
import java.util.Arrays;


public class Main {
    final static String groupAddress="224.0.147.1";
    public static void main(String[] args) throws IOException {

        MultiClass member= new MultiClass(groupAddress,8081);
        member.run();
//присоединиться к группе
    }
}


