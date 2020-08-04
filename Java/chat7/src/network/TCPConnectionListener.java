package network;

public interface TCPConnectionListener {
    void ConnectionReady(TCPConnection tcpConnection);
    void ReceiveMessage(TCPConnection tcpConnection, String message);
    void Disconnect (TCPConnection tcpConnection);
    void Exception (TCPConnection tcpConnection, Exception e);
    public int getConNum();
}
