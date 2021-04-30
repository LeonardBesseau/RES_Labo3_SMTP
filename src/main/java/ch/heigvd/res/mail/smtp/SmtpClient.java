package ch.heigvd.res.mail.smtp;

import ch.heigvd.res.mail.model.mail.Message;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

// Class representing the SMTP client that will send pranks to a SMTP server
public class SmtpClient {

    private final String smtpServerAddress;
    private final int serverPort;

    /**
     * Constructor of the class SmtpClient
     *
     * @param smtpServerAddress address of SMTP server
     * @param serverPort        port of SMTP server
     */
    public SmtpClient(String smtpServerAddress, int serverPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.serverPort = serverPort;
    }

    /**
     * Method for sending an email to the SMTP server
     *
     * @param message message to send
     */
    public void sendMessage(Message message) throws IOException {

        try (Socket socket = new Socket(smtpServerAddress, serverPort);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

            String line = reader.readLine();
            System.out.println("Connected to " + line);
            writer.printf("EHLO localhost\r\n");
            writer.flush();
            line = reader.readLine();
            if (!line.startsWith("250")) {
                throw new IOException("SMTP error: " + line);
            }

            // Reading all the informations send by the server before writing something
            while (line.startsWith("250-")) {
                line = reader.readLine();
            }

            // Setting up sender
            writer.write("MAIL FROM:" + message.getFrom() + "\r\n");
            writer.flush();
            line = reader.readLine();

            // Setting up recipients
            for (String to : message.getTo()) {
                writer.write("RCPT TO:<" + to + ">\r\n");
                writer.flush();
                line = reader.readLine();
                if (!line.startsWith("250")) {
                    System.err.println("Could not send email to " + to + ". Reason " + line);
                } else {
                    System.out.println("Sending to " + to + ".");
                }
            }

            // Setting up blind copies
            for (String bcc : message.getBcc()) {
                writer.write("RCPT TO:" + bcc + "\r\n");
                writer.flush();
                line = reader.readLine();
                if (!line.startsWith("250")) {
                    System.err.println("Could not send email to " + bcc + ". Reason " + line);
                } else {
                    System.out.println("Sending to (hidden) " + bcc + ".");
                }
            }

            // Filling up body of email
            writer.write("DATA\r\n");
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
            writer.write("\r\n.\r\n"); // To indicate end of body
            writer.flush();
            line = reader.readLine();
            writer.write("QUIT\r\n");
            writer.flush();
            System.out.println("DONE\n");
        }
        catch (ConnectException e) {
            throw new RuntimeException("SMTP server is not available or reachable. " + e.getMessage());
        }
        catch (IOException e) {
            throw new RuntimeException("A problem occurred with IO. " + e.getMessage());
        }
    }
}
