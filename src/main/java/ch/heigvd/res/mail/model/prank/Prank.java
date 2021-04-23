package ch.heigvd.res.mail.model.prank;

import ch.heigvd.res.mail.model.mail.Message;
import ch.heigvd.res.mail.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {

    private Person sender;
    private final List<Person> targets;
    private final List<Person> copy;
    private String message;

    public Prank() {
        sender = null;
        targets = new ArrayList<>();
        copy = new ArrayList<>();
        message = null;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public List<Person> getTargets() {
        return targets;
    }

    public void addTarget(List<Person> person){
        targets.addAll(person);
    }

    public List<Person> getCopy() {
        return copy;
    }

    public void addCopy(List<Person> person){
        copy.addAll(person);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message generateMessage(){
        Message msg = new Message();

        msg.setContent(message+"\r\n");
        msg.setTo(targets.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setBcc(copy.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setFrom(sender.getAddress());

        return msg;
    }
}
