package ch.heigvd.res.mail.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final List<Person> members;

    public Group() {
        this.members = new ArrayList<>();
    }

    public void addMember(Person person) {
        members.add(person);
    }

    public List<Person> getMembers() {
        return new ArrayList<>(members);
    }
}
