package model;

import java.util.HashMap;
import java.util.Map;

import exceptions.NewSingletonException;

public class MapRegles {
    
    private static MapRegles instance;

    // stock le nom de la regle en cle et une regle en valeur
    private Map<String, Regle> mapRegles = new HashMap<>();
    
    private MapRegles() {
        try {
            if(null != instance)
                throw new NewSingletonException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MapRegles getInstance() {
        if(null == instance) {
            instance = new MapRegles();
        }
        return instance;
    }
}
