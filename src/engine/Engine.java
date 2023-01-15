package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import constants.Constants;
import model.FactMap;
import model.FaitBoolean;
import model.FaitInteger;
import model.MapRegles;
import model.Regle;

/**
 * Cette classe est le moteur d'inférence que l'on utilisera pour évaluer les différentes
 * expressions de la map des règles.
 * 
 */
public class Engine {

    /**
     * Représente la moyenne des réponse calculé pour chaque regles. Représente ainsi la réponse finale.
     */
    public static boolean average;

    /**
     * Appel la méthode evaluate sur toute les regles de la map de regles
     * @param regles la map de regles que l'ont veut évaluer
     */
    public static void evaluateAllRules(Map<String, Regle> regles) {

        for(Map.Entry<String, Regle> regleEntry : regles.entrySet()) {
            System.out.println("BASE EXPRESSION : " + regleEntry.getKey());
            System.out.println("SI : " + regleEntry.getValue().getExpression() + " ALORS : " + regleEntry.getValue().getFact());
            System.out.println("evaluation : ");
            try {
                if(regleEntry.getValue().getFact().contains("!")) {
                    System.out.println("En bonne santé selon cette regle : " + !evaluate(regleEntry.getValue().getExpression()));
                    regleEntry.getValue().setEvaluation(!evaluate(regleEntry.getValue().getExpression()));
                } else {
                    System.out.println("En bonne santé selon cette regle : " + evaluate(regleEntry.getValue().getExpression()));
                    regleEntry.getValue().setEvaluation(evaluate(regleEntry.getValue().getExpression()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    public static boolean evaluate(String expression) {
        List<Boolean> bools = new ArrayList<>();

        StringTokenizer tokens = new StringTokenizer(expression, "&&");
        while(tokens.hasMoreTokens()) {
            String current = tokens.nextToken();
            if(!current.contains(">") && !current.contains("<")) {
                if(current.contains("!")) {
                    bools.add(!Boolean.parseBoolean(current.replace("!", "")));
                } else {
                    bools.add(Boolean.parseBoolean(current));
                }
            } else {
                bools.add(evaluateVarExpression(current));
            }
        }

        if(bools.contains(false)) {
            return false;
        }
        return true;
    }

    /**
     * Si le token est une expression avec des variable on appel cette méthode pour l'évaluer
     */
    public static boolean evaluateVarExpression(String s) {
        //81>100
        String delim = "";
        if(s.contains(">")) {
            delim = ">";
        } else if(s.contains("<")) {
            delim = "<";
        }
            
        StringTokenizer tokens = new StringTokenizer(s, delim);
        int param1 = Integer.parseInt(tokens.nextToken());
        int param2 = Integer.parseInt(tokens.nextToken());

        if(delim.equals(">")) {
            return param1 > param2;
        }
        return param1 < param2;

    }

    /**
     * Permet de traduire une expression d'une règle en une expression évaluable par la méthode
     * evaluate. Remplace aussi les différentes valeurs avec les valeurs connues dans la map des faits.
     * 
     * @param rule L'expression de la règle concernée
     * @return L'expression traduite avec remplacement des valeurs connues
     */
    public static String translate(String rule) {
        FactMap fm = FactMap.getInstance();

        String rawExpression = rule;

        String newExpression = "";
        for(String str : rawExpression.split(";")) {
            str = str.replace(Constants.IF, "");
            str = str.replace(Constants.AND, " && ");
            str = str.replace("NON(", "!");
            str = str.replace(")", "");
            if(str.startsWith(Constants.THEN)) {
                str = "";
            }
            str = str.replace(" ", "");
            newExpression += str;
        }

        for(Map.Entry<String, FaitBoolean> entry : fm.getFaitsBooleens().entrySet()) {
            if(entry.getValue() != null) {
                newExpression = newExpression.replace(entry.getKey(), "" + entry.getValue().getValue());
            }
        }

        for(Map.Entry<String, FaitInteger> entry : fm.getFaitsEntiers().entrySet()) {
            if(entry.getValue() != null) {
                newExpression = newExpression.replace(entry.getKey(), "" + entry.getValue().getValue());
            }
        }


        return newExpression;
    }

    /**
     * Parcours la map des regles et calculs la moyenne de réponses à ces regles.
     * 
     */
    public static void calculateAverageResponses() {
        List<Boolean> bools = new ArrayList<>();

        for(Map.Entry<String, Regle> regleEntry : MapRegles.getInstance().getMapRegles().entrySet()) {
            bools.add(regleEntry.getValue().getEvaluation());
        }

        int falseOccurences = Collections.frequency(bools, false);
        System.out.println(falseOccurences + ", " + bools.size());
        for(boolean bool : bools) {
            System.out.println(bool);
        }

        Engine.average = falseOccurences < bools.size() / 2;
    }
    
}
