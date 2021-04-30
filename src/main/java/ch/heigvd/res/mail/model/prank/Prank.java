package ch.heigvd.res.mail.model.prank;

import ch.heigvd.res.mail.model.mail.Message;
import ch.heigvd.res.mail.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Class representing a prank
public class Prank {

    private Person sender;
    private final List<Person> targets;
    private final List<Person> copy;
    private String message;

    /**
     * Constructor of the class Prank
     */
    public Prank() {
        sender = null;
        targets = new ArrayList<>();
        copy = new ArrayList<>();
        message = null;
    }*/

    /**
     * Method to fill the targets
     * @param person list of targets
     */
    public void addTarget(List<Person> person) {
        targets.addAll(person);
    }

    /**
     * Method to fill the people in blind copy
     * @param person list of Person in blind copy
     */
    public void addCopy(List<Person> person) {
        copy.addAll(person);
    }

    /**
     * Method that will generate a Message with all the information gathered
     * @return Message object that will be sent
     */
    public Message generateMessage() {
        Message msg = new Message();

        msg.setContent(message + "\r\n");
        msg.setTo(targets.stream().map(Person::getEmail).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setBcc(copy.stream().map(Person::getEmail).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setFrom(sender.getEmail());

        return msg;
    }
}
