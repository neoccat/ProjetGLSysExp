package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import constants.Constants;
import exceptions.NewSingletonException;
import model.Fait;
import model.FaitBoolean;
import model.FaitInteger;
import model.FaitSymbollique;


/**
 * static parser pour le fichier connaissances
 */
public final class Parser {

    private static Parser instance = new Parser();
    private static BufferedReader reader = null;

    private Parser() {
        try {
            if(this.instance != null) throw new NewSingletonException();
        } catch(NewSingletonException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Parser getParser() {
        return instance;
    }

    /**
     * Retrouve la ligne ou les faits sont instancié selon le type
     * @param file
     * @param type
     * @return
     * @throws IOException
     */
    public String[] findFactLineAndProcess(String file, String type) throws IOException {
        // parse le fichier en paramètre et initialise la map des faits
        reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        while(line != null) {
            if(line.contains(type)) {
                break;
            }
            line = reader.readLine();
        }

        line = line.replace("faits_" + type + " = ", "");
        line = line.replace(" ", "");
        line = line.replace(";", "");
        String[] splitted = line.split(",");

        System.out.println(line);
        return splitted;
    }

    public Map<String, FaitBoolean> initialiseFaitsBoolean(String file) throws IOException {

        String[] splitted = findFactLineAndProcess(file, Constants.FAIT_BOOLEENS); 

        Map<String, FaitBoolean> mapFaits = new HashMap<>();
        for(String str : splitted) {
            mapFaits.put(str, null);
        }

        return mapFaits;
    }

    public Map<String, FaitInteger> initialiseFaitsEntier(String file) {
        // parse le fichier en paramètre et initialise la map des faits
        return new HashMap<>();
    }

    public Map<String, FaitSymbollique> initialiseFaitsSymbolique(String file) {
        // parse le fichier en paramètre et initialise la map des faits
        return new HashMap<>();
    }
}
