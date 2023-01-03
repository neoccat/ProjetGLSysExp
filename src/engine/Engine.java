package engine;

import java.util.Map;

import javax.script.ScriptException;

import constants.Constants;
import model.FactMap;
import model.FaitBoolean;
import model.FaitInteger;
import model.Regle;

/**
 * Cette classe est le moteur d'inférence que l'on utilisera pour évaluer les différentes
 * expressions de la map des règles.
 * 
 */
public class Engine {


    public static void evaluateAllRules(Map<String, Regle> regles) {

        for(Map.Entry<String, Regle> regleEntry : regles.entrySet()) {
            System.out.println(translate(regleEntry.getValue().getExpression()));
            System.out.println("evaluation : ");
            try {
                System.out.println(evaluate(translate(regleEntry.getValue().getExpression())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //regleEntry.getValue().setEvaluation(evaluate(regleEntry.getKey()));
        }
    }

    public static Object evaluate(String expression) throws ScriptException {
        //SI fumeur;ET NON(fait_sport);ALORS NON(en_bonne_sante)
        return false;
    }

    public static String translate(String rawExpression) {
        FactMap fm = FactMap.getInstance();

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

    public static boolean isIfStatement(String statement) {
        return statement.startsWith(Constants.IF);
    }

    public static boolean isAndStatement(String statement) {
        return statement.startsWith(Constants.AND);
    }

    public static boolean isThenStatement(String statement) {
        return statement.startsWith(Constants.THEN);
    }
    
}
