package model;

import java.util.HashMap;
import java.util.Map;

import exceptions.NewSingletonException;

public class FactMap {
    
    private static FactMap instance;
    private Map<String, FaitBoolean> faitsBooleens = new HashMap<>();
    private Map<String, FaitInteger> faitEntiers = new HashMap<>();
    private Map<String, FaitSymbolique> faitsSymboliques = new HashMap<>();

    private FactMap() {
        try {
            if(null != instance)
                throw new NewSingletonException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FactMap getInstance() {
        if(null == instance) {
            instance = new FactMap();
        }
        return instance;
    }

    public Map<String, FaitBoolean> getFaitsBooleens() {
        return faitsBooleens;
    }

    public Map<String, FaitInteger> getFaitsEntiers() {
        return faitEntiers;
    }

    public Map<String, FaitSymbolique> getFaitsSymboliques() {
        return faitsSymboliques;
    }

    public void setFaitsBooleens(Map<String, FaitBoolean> faitsBooleens) {
        this.faitsBooleens = faitsBooleens;
    }

    public void setFaitEntiers(Map<String, FaitInteger> faitEntiers) {
        this.faitEntiers = faitEntiers;
    }

    public void setFaitsSymboliques(Map<String, FaitSymbolique> faitsSymboliques) {
        this.faitsSymboliques = faitsSymboliques;
    }

}
