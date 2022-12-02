package exceptions;

public class NewSingletonException extends Exception {
    public NewSingletonException() {
        super("On essaye de créer un nouveau singleton : pas possible");
    }

}
