package ch.heigvd.res.mail.smtp;

import ch.heigvd.res.mail.model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {
    public void sendMessage(Message message) throws IOException;
}
