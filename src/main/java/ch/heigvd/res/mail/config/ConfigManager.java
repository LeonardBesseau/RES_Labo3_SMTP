package ch.heigvd.res.mail.config;

import ch.heigvd.res.mail.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.validator.routines.EmailValidator;

public class ConfigManager {
    private String smtpServerAddress;
    private int serverPort;
    private final List<Person> targets;
    private final List<String> messages;
    private int nbGroups;
    private List<Person> copy;

    public ConfigManager(String pathToConfig) throws IOException {
        targets = loadAddressesFromFile(pathToConfig + "/victims.utf8");
        messages = loadMessagesFromFile(pathToConfig + "/messages.utf8");
        loadProperties(pathToConfig + "/config.properties");
    }

    private void loadProperties(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        Properties properties = new Properties();
        properties.load(fis);
        smtpServerAddress = properties.getProperty("smtpServerAddress");
        if (smtpServerAddress.isEmpty()) {
            throw new RuntimeException("Mail server cannot be empty");
        }
        try {
            serverPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Server port must be a number and non-empty");
        }
        if(serverPort <= 0 || serverPort >= 65535){
            throw new RuntimeException("Port number must be between 1 and 65535. Careful port 1-1000 need admin privileges");
        }

        try {
            nbGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("The number of group must be a number and non-empty");
        }
        if(nbGroups <= 0){
            throw new RuntimeException("The number of group should be greater than 0");
        }

        copy = new ArrayList<>();
        String cc = properties.getProperty("BCC");
        String[] ccAddresses = cc.split(",");
        for (String email : ccAddresses) {
            if(email.isEmpty()){
                continue;
            }
            if (EmailValidator.getInstance().isValid(email)) {
                copy.add(new Person(email));
            } else {
                throw new RuntimeException("Email in Bcc must be valid");
            }
        }

    }

    private List<Person> loadAddressesFromFile(String filename) throws IOException {
        List<Person> result;
        try (FileInputStream fis = new FileInputStream(filename)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String address = reader.readLine();
                while (address != null) {
                    result.add(new Person(address));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    private List<String> loadMessagesFromFile(String filename) throws IOException {
        List<String> result;
        try (FileInputStream fis = new FileInputStream(filename)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String line = reader.readLine();
                while (line != null) {
                    StringBuilder body = new StringBuilder();
                    while ((line != null) && (!line.equals("___"))) {
                        body.append(line);
                        body.append("\r\n");
                        line = reader.readLine();
                    }
                    result.add(body.toString());
                    line = reader.readLine();
                }
            }
        }
        return result;
    }


    public List<Person> getTargets() {
        return targets;
    }


    public List<String> getMessages() {
        return messages;
    }


    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }


    public int getServerPort() {
        return serverPort;
    }


    public int getNbGroups() {
        return nbGroups;
    }


    public List<Person> getCopy() {
        return copy;
    }
}
