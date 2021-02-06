import java.io.Serializable;

public enum MessageType implements Serializable {
    MESSAGE,//simple message
    CONNECTION,//message for connection start
    ANSWER,//message for receiving confirmation
    REPLACE_INFO//message with replacement node address
}
