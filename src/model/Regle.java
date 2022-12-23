package model;

public class Regle {
    
    // Expression de la regles interpretable par le moteur d'inférence
    private String expression;

    // Résultat de l'évaluation renvoyée par le moteur d'inférence
    private String evaluation;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    
}
