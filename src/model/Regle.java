package model;

import constants.Constants;
import engine.Engine;

public class Regle {
    
    //Expression de la règle
    private String rawExpression;

    // Expression de la regle interpretable par le moteur d'inférence
    private String expression;

    // Code du fait à évaluer
    private String fact;

    // Résultat de l'évaluation renvoyée par le moteur d'inférence
    private Boolean evaluation;

    

    public Regle(String expression) {
        this.rawExpression = expression;
        this.expression = Engine.translate(expression);
        this.setFact();
    }

    public Regle(String expression, Boolean eval) {
        this(expression);
        this.evaluation = eval;
    }

    /**
     * Set le fait concerner par la règle en la recherchant dans la map des faits (conclusion)
     */
    public void setFact() {
        String[] expr = this.rawExpression.split(";");
        for(String str : expr) {
            if(str.startsWith(Constants.THEN)) {
                this.fact = str.split(" ")[1].replace("NON(", "!").replace(")", "");
            }
        }
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

    public String  getFact() {
        return fact;
    }

    

    
}
