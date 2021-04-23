package ch.heigvd.res.mail.model.mail;

public class Message {
    private String from;
    private String[] to;
    private String[] bcc;
    private String content;

    public Message(){
        to = new String[0];
        bcc = new String[0];
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
