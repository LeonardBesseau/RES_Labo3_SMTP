package ch.heigvd.res.mail.model.mail;

import java.util.ArrayList;
import java.util.List;

// Class representing a group of victims
public class Group {
    private final List<Person> members;

    /**
     * Constructor of the class Group
     */
    public Group() {
        this.members = new ArrayList<>();
    }

    /**
     * Method that will add a Person to the group
     * @param person
     */
    public void addMember(Person person) {
        members.add(person);
    }

    /**
     * Method to get the members of the groups
     * @return list of Person in the group
     */
    public List<Person> getMembers() {
        return new ArrayList<>(members);
    }
}
