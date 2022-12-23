import java.io.File;
import java.io.IOException;

import exceptions.NewSingletonException;
import parser.Parser;

public class Main {
    
    public static void main(String[] args) throws IOException, NewSingletonException {
        Parser parser = Parser.getParser();
        
        parser.initialiseBaseDeFaits(args[0]);
    }
}
