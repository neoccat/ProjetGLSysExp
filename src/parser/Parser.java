package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import constants.Constants;
import exceptions.NewSingletonException;
import model.FactMap;
import model.FaitBoolean;
import model.FaitInteger;
import model.FaitSymbolique;
import utils.Utils;


/**
 * static parser pour le fichier baseDeConnaissance.txt
 */
public final class Parser {

    private static Parser instance;
    private static BufferedReader reader = null;

    private Parser() {
        try {
            if(null != instance)
                throw new NewSingletonException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Parser getParser() {
        if(null == instance) {
            instance = new Parser();
        }
        return instance;
    }

    /**
     * Retrouve la ligne ou les faits sont instancié selon le type
     * @param file Le nom du fichier
     * @param type le type de fait (Ex : booleens, symboliques, entiers)
     * @return String : retourne la ligne concernée
     * @throws IOException
     */
    public String[] findFactLineAndProcess(String file, String type) throws IOException {

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

        reader.close();
        return splitted;
    }

    /**
     * Fait appel au 3 méthodes suivantes pour initialiser les maps de faits
     * @param file le nom du fichier 
     * @throws IOException
     * @throws NewSingletonException
     */
    public void initialiseFacts(String file) throws IOException {
        FactMap fm = FactMap.getInstance();

        fm.setFaitsBooleens((Map<String, FaitBoolean>)initialiseFaitsType(file, Constants.FAITS_BOOLEENS));
        fm.setFaitEntiers((Map<String, FaitInteger>)initialiseFaitsType(file, Constants.FAITS_ENTIERS));
        fm.setFaitsSymboliques((Map<String, FaitSymbolique>)initialiseFaitsType(file, Constants.FAITS_SYMBOLIQUES));
    }

    /**
     * Initialise une map de fait selon le type de faits
     * 
     * note : j'ai préféré faire une méthode générique pour l'instanciation des maps au lieu de faire 3 méthodes différentes
     *        pour éviter la redondance de code. Cela implique la génération de potentielles erreurs mais qui sont traitées
     *        dans des exceptions.
     * 
     * @param file
     * @param type
     * @return
     * @throws IOException
     */
    public Map<String, ?> initialiseFaitsType(String file, String type) throws IOException {

        String[] splitted = findFactLineAndProcess(file, type);

        Map<String, ?> mapFaits = null;
        switch(type) {
            case Constants.FAITS_BOOLEENS:
                mapFaits = new HashMap<String, FaitBoolean>();
                break;
            case Constants.FAITS_ENTIERS:
                mapFaits = new HashMap<String, FaitInteger>();
                break;
            case Constants.FAITS_SYMBOLIQUES:
                mapFaits = new HashMap<String, FaitSymbolique>();
                break;
        }

        if(null == mapFaits) {
            mapFaits = new HashMap<>();
        }

        for(String str : splitted) {
            mapFaits.put(str, null);
        }

        return mapFaits;
    }


    /* --------------------------------------------------------------------------- */
    /*                     INITIALISATION DE LA BASE DE FAITS                      */
    /* --------------------------------------------------------------------------- */

    /**
     * Initialise les différentes valeurs de la base de fait en passant les valeurs en entrée aux
     * différentes maps.
     * 
     * @param file
     * @throws IOException
     * @throws NewSingletonException
     */
    public void initialiseBaseDeFaits(String file) throws IOException, NewSingletonException {
        
        initialiseFacts(file);
        reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        while(!line.contains(Constants.BASE_DE_FAITS_OUVERTURE)) {
            line = reader.readLine();
        }
        line = reader.readLine();

        StringBuilder toSplit = new StringBuilder();
        while(!line.contains(Constants.BASE_DE_FAITS_FERMETURE)) {
            toSplit.append(line);
            line = reader.readLine();
        }

        
        processBaseDeFait(toSplit.toString().split(";"));
        
        System.out.println("\nAFTER : \n");
        FactMap fm = FactMap.getInstance();
        for(Map.Entry<String, FaitBoolean> entry : fm.getFaitsBooleens().entrySet() ) {
            System.out.println("[" + entry.getKey() + ", " + entry.getValue() + "]");
        }

        System.out.println("\n");

        for(Map.Entry<String, FaitInteger> entry : fm.getFaitsEntiers().entrySet() ) {
            System.out.println("[" + entry.getKey() + ", " + entry.getValue() + "]");
        }

        System.out.println("\n");

        for(Map.Entry<String, FaitSymbolique> entry : fm.getFaitsSymboliques().entrySet() ) {
            System.out.println("[" + entry.getKey() + ", " + entry.getValue() + "]");
        }

        System.out.println("\n");
    }

    public void processBaseDeFait(String[] faits) {
        FactMap fm = FactMap.getInstance();

        for( String fait : faits ) {

            // Si le fait est directement présent en map je le passe à true
            if( fm.getFaitsBooleens().containsKey(fait) ) {
                fm.getFaitsBooleens().put(fait, new FaitBoolean(fait, true));

            // si le fait est une négation
            } else if(
                fm.getFaitsBooleens().containsKey(
                    fait.replace("NON(", "").replace(")", "")
                )
            ) {
                String faitArrange = fait.replace("NON(", "").replace(")", "");
                fm.getFaitsBooleens().put(faitArrange, new FaitBoolean(faitArrange, false));
            
            // Si le fait est un fait entier
            } else if(
                fait.contains("=")
                && Utils.isNumeric(fait.split("=")[1].replace(";", "").replace(" ", ""))
            ) {
                String faitArrange = fait.split("=")[0].replace(" ", "");
                fm.getFaitsEntiers().put(
                    faitArrange,
                    new FaitInteger(faitArrange, Integer.parseInt(fait.split("=")[1].replace(";", "").replace(" ", "")))
                );
            
            // Si le fait est un fait symolique
            } else if(
                fait.contains("=")
                && !Utils.isNumeric(fait.split("=")[1].replace(";", ""))
            ) {
                String faitArrange = fait.split("=")[0].replace(" ", "");
                fm.getFaitsSymboliques().put(
                    faitArrange,
                    new FaitSymbolique(faitArrange, fait.split("=")[1].replace(" ", "").replace(";", ""))
                );
            }
        }
    }

    



}
