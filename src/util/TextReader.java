package util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TextReader {
    public static String readFileToString(String filename) throws Exception
    {
        String output = "";
        output = new String(Files.readAllBytes(Paths.get(filename)));
        return output;
    }
}
