package ch.heigvd.res.mail.config;

import ch.heigvd.res.mail.model.mail.Person;

import java.util.List;

public interface IConfigManager {

    public List<Person> getTargets();

    public List<String> getMessages();

    public String getSmtpServerAddress();

    public int getServerPort();

    public int getNbGroups();

    public List<Person> getCopy();
}
