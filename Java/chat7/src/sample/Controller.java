package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.TCPConnection;
import network.TCPConnectionListener;
import server.ChatServer;
import java.net.Socket;

import java.io.IOException;

public class Controller implements TCPConnectionListener {
    private Stage primaryStage;
    String userName;
    Boolean connect=false;
    Integer MAX=Integer.MAX_VALUE;
    TCPConnection con;
    @FXML
    public StackPane pane;
    @FXML
    TextField ipLab;
    @FXML
    TextField portLab;
    @FXML
    public Label nickname;
    @FXML
    AnchorPane menuPane;
    @FXML
    TextArea chat;
    @FXML
    TextField mes;
    @FXML
    AnchorPane chatPane;
    @FXML
    Button serverBut;
    @FXML
    Button clientBut;
    @FXML
    TextField nicknameEnter;
    @FXML
    Label chatName;
    public void setStage(Stage stage) {
        primaryStage = stage;

    }
    public void showFieldForNickname(){
        nicknameEnter.setVisible(true);
    }
    public  void startServer(){
        new ChatServer();

    }
    public void startConnection(){
        String ip = ipLab.getText();
        int port = Integer.parseInt(portLab.getText());
        try {
            con=new TCPConnection(this, ip, port,nickname.getText());
            mes.setEditable(true);
        } catch (IOException e) {
            printMes("connection exception"+e);
        }
        chat.positionCaret(MAX);
        //chatName.setEditable
    }
    public void keyHandler(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.ENTER) {
            if (menuPane.isVisible()&&nicknameEnter.isVisible()){
                menuPane.setVisible(false);
                chatPane.setVisible(true);
                nicknameEnter.setVisible(false);
                if(nicknameEnter.getText().equals("")) {
                    userName="user"+ 1;
                } else {userName=nicknameEnter.getText();}
                nickname.setText(userName); }
            else {
                if(mes.getText()!=""){
                    con.sendMessage(/*nickname.getText()+" : "+*/mes.getText());
                    //printMes(mes.getText());
                    mes.setText("");
                }
            }
        }

        //event.consume();
        return;
    }

    @Override
    public void ConnectionReady(TCPConnection tcpConnection) {
        printMes("connected");
    }

    @Override
    public void ReceiveMessage(TCPConnection tcpConnection, String message) {
        printMes(message);
    }

    @Override
    public void Disconnect(TCPConnection tcpConnection) {
        con.sendMessage(nickname.getText()+" disconnected");
    }

    @Override
    public void Exception(TCPConnection tcpConnection, Exception e) {
        printMes("connection exception "+e);
    }
    public void printMes(String message){
//тут лучше какой-нибудь инвок четатам
        if((message!=null)&&(!message.equals("null")))chat.appendText(message+"\n");

    }
    public int getConNum() {return 0;
    }
}
