package ch.heigvd.res.mail.model.mail;

import lombok.Getter;
import lombok.Setter;

// Class representing a message to send to a SMTP server
@Getter
@Setter
public class Message {

    
    private String from;
    private String[] to;
    private String[] bcc;
    private String content;

    /**
     * Constructor of class Message
     */
    public Message() {
        to = new String[0];
        bcc = new String[0];
    }
}
