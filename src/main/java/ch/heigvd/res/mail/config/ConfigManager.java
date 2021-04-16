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

public class ConfigManager implements IConfigManager {
    private String smtpServerAddress;
    private int serverPort;
    private final List<Person> targets;
    private final List<String> messages;
    private int nbGroups;
    private List<Person> copy;

    public ConfigManager(String pathToConfig) throws IOException {
        targets = loadAddressesFromFile(pathToConfig + "/victims.utf8");
        messages = loadMessagesFromFile(pathToConfig + "/messages.utf8");
        loadProperties(pathToConfig + "config.properties");
    }

    private void loadProperties(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        Properties properties = new Properties();
        properties.load(fis);
        smtpServerAddress = properties.getProperty("smtpServerAddress");
        serverPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        nbGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));

        copy = new ArrayList<>();
        String cc = properties.getProperty("witnessToCC");
        String[] ccAddresses = cc.split(",");
        for (String address : ccAddresses) {
            copy.add(new Person(address));
        }

    }

    private List<Person> loadAddressesFromFile(String filename) throws IOException{
        List<Person> result;
        try(FileInputStream fis = new FileInputStream(filename)){
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try(BufferedReader reader = new BufferedReader(isr)){
                result = new ArrayList<>();
                String address = reader.readLine();
                while (address != null){
                    result.add(new Person(address));
                    address= reader.readLine();
                }
            }
        }
        return result;
    }
    private List<String> loadMessagesFromFile(String filename) throws IOException{
        List<String> result;
        try(FileInputStream fis = new FileInputStream(filename)){
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try(BufferedReader reader = new BufferedReader(isr)){
                result = new ArrayList<>();
                String line = reader.readLine();
                while (line != null){
                    StringBuilder body = new StringBuilder();
                    while ((line != null) && (!line.equals("=="))){
                        body.append(line);
                        body.append("\r\n");
                        line = reader.readLine();
                    }
                    result.add(body.toString());
                    line= reader.readLine();
                }
            }
        }
        return result;
    }

    @Override
    public List<Person> getTargets() {
        return targets;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public int getNbGroups() {
        return nbGroups;
    }

    @Override
    public List<Person> getCopy() {
        return copy;
    }
}
