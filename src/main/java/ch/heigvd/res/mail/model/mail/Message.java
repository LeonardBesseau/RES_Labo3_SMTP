package ch.heigvd.res.mail.model.mail;

// Class representing a message to send to a SMTP server
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

    /**
     * Getter for the sender
     * @return email of sender
     */
    public String getFrom() {
        return from;
    }

    /**
     * Setter for the sender
     * @param from email of sender
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Getter for the recipients
     * @return array of recipients emails
     */
    public String[] getTo() {
        return to;
    }

    /**
     * Setter for the recipients
     * @param to array of recipients emails
     */
    public void setTo(String[] to) {
        this.to = to;
    }

    /**
     * Getter of the recipients in blind copy
     * @return array of recipients emails in blind copy
     */
    public String[] getBcc() {
        return bcc;
    }

    /**
     * Setter of the recipients in blind copy
     * @param bcc array of recipients emails in blind copy
     */
    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    /**
     * Getter of content of the message
     * @return content of message
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter of content of the message
     * @param content content of message
     */
    public void setContent(String content) {
        this.content = content;
    }
}
