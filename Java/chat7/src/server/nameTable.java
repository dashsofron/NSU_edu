package server;

import java.net.Socket;
import java.util.Map;

public class nameTable {
    static Map<Socket,String> names;
    public nameTable(){
        names=new Map<Socket,String>();
    }
}
