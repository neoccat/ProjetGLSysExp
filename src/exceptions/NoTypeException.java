package exceptions;

public class NoTypeException extends Exception {
    public NoTypeException() {
        super("Le système n'as pas pu évaluer le type d'entrées d'une des listes de faits.");
    }
}
