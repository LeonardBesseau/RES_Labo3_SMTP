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

// Class for reading config files to setup the configuration of the pranks campaign
public class ConfigManager {

    private String smtpServerAddress;
    private int serverPort;
    private final List<Person> targets;
    private final List<String> messages;
    private int nbGroups;
    private List<Person> copy;

    /**
     * Constructor that setup the targets and the messages according to the config files
     * @param pathToConfig path to config directory
     */
    public ConfigManager(String pathToConfig) throws IOException {
        targets = loadAddressesFromFile(pathToConfig + "/victims.utf8");
        messages = loadMessagesFromFile(pathToConfig + "/messages.utf8");
        loadProperties(pathToConfig + "/config.properties");
    }

    /**
     * Method that will setup the configuration of the SMTP client according to the config file
     * @param fileName name of the config file
     */
    private void loadProperties(String fileName) throws IOException {

        FileInputStream fis = new FileInputStream(fileName);

        // Parsing properties in config file
        Properties properties = new Properties();
        properties.load(fis);
        smtpServerAddress = properties.getProperty("smtpServerAddress");
        if (smtpServerAddress.isEmpty()) {
            throw new RuntimeException("Mail server cannot be empty");
        }
        try {
            serverPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("Server port must be a number and non-empty");
        }

        if (serverPort <= 0 || serverPort >= 65535) {
            throw new RuntimeException("Port number must be between 1 and 65535. Careful port 1-1024 need admin privileges");
        }

        try {
            nbGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("The number of group must be a number and non-empty");
        }

        if (nbGroups <= 0) {
            throw new RuntimeException("The number of group should be greater than 0");
        }

        copy = new ArrayList<>();
        String cc = properties.getProperty("BCC");
        String[] ccAddresses = cc.split(",");
        for (String email : ccAddresses) {
            if (email.isEmpty()) {
                continue;
            }
            if (EmailValidator.getInstance().isValid(email)) {
                copy.add(new Person(email));
            } else {
                throw new RuntimeException("Email in Bcc must be valid");
            }
        }

    }

    /**
     * Method for parsing email addresses in the victims file
     * @param filename victims file
     * @return list of victims addresses
     */
    private List<Person> loadAddressesFromFile(String filename) throws IOException {
        List<Person> result;
        try (FileInputStream fis = new FileInputStream(filename)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String address = reader.readLine();

                while (address != null && !address.isEmpty()) {
                    result.add(new Person(address));
                    address = reader.readLine();
                }
            }
        }
        if (result.size() < 3) {
            throw new RuntimeException("Need at least 3 person to target");
        }
        return result;
    }

    /**
     * Method for parsing messages in the messages file
     * @param filename messages file
     * @return list of messages
     */
    private List<String> loadMessagesFromFile(String filename) throws IOException {
        List<String> result;
        try (FileInputStream fis = new FileInputStream(filename)) {
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String line = reader.readLine();

                while (line != null) {
                    StringBuilder body = new StringBuilder();

                    // Messages are separated by "___"
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
        if (result.isEmpty()) {
            throw new RuntimeException("Need at least one message");
        }
        return result;
    }

    /**
     * Getter for the targets
     * @return list of targets addresses
     */
    public List<Person> getTargets() {
        return targets;
    }

    /**
     * Getter for the messages
     * @return list of messages
     */
    public List<String> getMessages() {
        return messages;
    }


    /**
     * Getter for the SMTP server address
     * @return SMTP server address
     */
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    /**
     * Getter for the SMTP server port
     * @return SMTP server port
     */
    public int getServerPort() {
        return serverPort;
    }


    /**
     * Getter for the number of victims groups
     * @return number of victims groups
     */
    public int getNbGroups() {
        return nbGroups;
    }

    /**
     * Getter for the people in blind copy
     * @return list of people addresses in blind copy
     */
    public List<Person> getCopy() {
        return copy;
    }
}
