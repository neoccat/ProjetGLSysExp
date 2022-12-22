import java.io.File;
import java.io.IOException;

import parser.Parser;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Parser parser = Parser.getParser();
        parser.initialiseFaitsBoolean(args[0]);
    }
}
