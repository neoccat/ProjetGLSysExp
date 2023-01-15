import java.io.IOException;

import engine.Engine;
import exceptions.NewSingletonException;
import model.MapRegles;
import parser.Parser;

public class Main {
    
    public static void main(String[] args) throws IOException, NewSingletonException {
        Parser parser = Parser.getParser();
        
        parser.initialiseBaseDeFaits(args[0]);
        parser.initialiseBaseDeRegles(args[0]);

        Engine.evaluateAllRules(MapRegles.getInstance().getMapRegles());

        Engine.calculateAverageResponses();

        System.out.println();
        System.out.println("RESULTATS : ");

        if(Engine.average) {
            System.out.println("Selon le programme, l'individu est globalement en bonne santé.");
        } else {
            System.out.println("Selon le programme, l'individu est globalement en mauvaise santé.");
        }

    }
}
