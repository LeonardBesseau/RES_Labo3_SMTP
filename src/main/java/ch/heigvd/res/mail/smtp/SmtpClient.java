package ch.heigvd.res.mail.smtp;

import ch.heigvd.res.mail.model.mail.Message;

import java.io.*;
import java.net.Socket;

public class SmtpClient {
    private final String smtpServerAddress;
    private final int serverPort;


    public SmtpClient(String smtpServerAddress, int serverPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.serverPort = serverPort;
    }

    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(smtpServerAddress, serverPort);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line = reader.readLine();
        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }
        while (line.startsWith("250-")) {
            line = reader.readLine();
        }

        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();

        for (String to : message.getTo()) {
            writer.write("RCPT TO:<");
            writer.write(to);
            writer.write(">\r\n");
            writer.flush();
            line = reader.readLine();
            // TODO check if RCPT is accepted
        }

        for (String bcc : message.getBcc()) {
            writer.write("RCPT TO:");
            writer.write(bcc);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            // TODO check if RCPT is accepted
        }

        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        writer.write("Content-type: text/plain; charset=\"utf-8\"\r\n");
        writer.write("From: " + message.getFrom() + "\r\n");

        writer.write("To: " + message.getTo()[0] + " ");
        for (int i = 1; i < message.getTo().length; i++) {
            writer.write(", " + message.getTo()[i]);
        }

        writer.write("\r\n");

        writer.flush();
        writer.write(message.getContent());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        writer.write("QUIT\r\n");
        writer.flush();
        socket.close();
    }
}
