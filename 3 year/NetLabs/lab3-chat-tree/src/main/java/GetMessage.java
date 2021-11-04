import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

//Хэндлер для считывания с консоли в отдельном потоке и передачи сообщения для вывода
public class GetMessage implements Runnable {
    //сообщения, на которые ждем ответ. Нужно знать номер и от кого
    //private BlockingQueue<MessageTracking> messagesForTracking;
    //сообщения которые надо отправить. Нужно знать текст сообщения, тип и кому
    private BlockingQueue<MessageSending> messagesForSending;

    public GetMessage(BlockingQueue<MessageSending> messagesForSending/*, BlockingQueue<MessageTracking> messagesForTracking*/) {
        this.messagesForSending = messagesForSending;
    }

    //считывание сообщения с консоли
    public String getMessage() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void run() {


        while (true) {
            //пытаемся считать сообщение, если его ввели в консоль
            String message = getMessage();
            if (message != null) {
                //считали сообщение. Его надо распечатать и отправить всем
                //добавляем сообщение в очередь.
                //Номер сообщения и имя отправителя мы не знаем, ставим null и они заполнятся при отправке (либо не указываем, выберется нужный конструктор)
                //Тип сообщения не указываем - здесь он автоматически простое сообщение, т.к. считываем с консоли только сообщения
                //Отправляем всем связанным узлам, т.к. ни от кого не пересылаем, все получат один раз
                //сокет не указываем, т.к. отправка всем
                messagesForSending.add(new MessageSending(new Message(message), ReceiversType.ALL));
            }
        }
    }
}