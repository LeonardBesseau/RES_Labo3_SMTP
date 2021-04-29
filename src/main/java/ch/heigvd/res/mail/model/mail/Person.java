package ch.heigvd.res.mail.model.mail;

// Class representing a Person with a email
public class Person {
    private String email;

    /**
     * Constructor of the class Person
     * @param email email of the person
     */
    public Person(String email) {
        this.email = email;
    }

    /**
     * Getter for the email of the Person
     * @return email of the Person
     */
    public String getEmail() {
        return email;
    }
}
