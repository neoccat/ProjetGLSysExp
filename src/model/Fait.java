package model;

/**
 * Classe Fait : Generique, un fait peut être entier, booleen ou symbolique.
 */
public abstract class Fait<T> {
    
    // nom du fait
    protected String code;
    // Valeur du fait (true, false pour un boolean, 81 pour entier...)
    protected T value;

    public Fait(String code) {
        this.code = code;
    }

    public Fait(String code, T value) {
        this(code);
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public boolean equals(Fait<T> fait) {
        return this.value.equals(fait.value) && this.code.equals(fait.code);
    }
}
