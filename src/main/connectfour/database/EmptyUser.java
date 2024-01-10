package connectfour.database;

/**
 * Implements the null object pattern for handling cases where a User is not found, in order to avoid returning null
 *
 * @author Raffaele Talente
 */
public class EmptyUser extends User {
    public EmptyUser(String name, String surname) {
        super(name, surname);
    }
}
