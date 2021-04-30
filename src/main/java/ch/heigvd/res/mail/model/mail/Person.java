package ch.heigvd.res.mail.model.mail;

import lombok.Getter;

// Class representing a Person with a email
@Getter
public class Person {

    private final String email;

    /**
     * Constructor of the class Person
     * @param email email of the person
     */
    public Person(String email) {
        this.email = email;
    }
}
