package ch.heigvd.res.mail.smtp;

import ch.heigvd.res.mail.model.mail.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SmtpClient implements ISmtpClient{
    private String smtpServerAddress;
    private int serverPort =25;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(String smtpServerAddress, int serverPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void sendMessage(Message message) throws IOException {

    }
}
