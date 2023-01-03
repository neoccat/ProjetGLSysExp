package model;

public class Regle {
    
    // Expression de la regles interpretable par le moteur d'inférence
    private String expression;

    // Résultat de l'évaluation renvoyée par le moteur d'inférence
    private Boolean evaluation;

    

    public Regle(String expression) {
        this.expression = expression;
    }

    public Regle(String expression, Boolean eval) {
        this(expression);
        this.evaluation = eval;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Boolean getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Boolean evaluation) {
        this.evaluation = evaluation;
    }

    
}
