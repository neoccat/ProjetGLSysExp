package constants;

public enum Constants {
    FAITS_BOOLEAN_VARIABLE("faits_booleens");

    private String constant;

    Constants(String cst) {
        this.constant = cst;
    }

    public String get() {
        return constant;
    }
    
}
