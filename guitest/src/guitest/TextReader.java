package guitest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.*;

public class TextReader {
    public static String ReadFileToText(File f1) {

        try {
            String textHolder; //holds current text being read
            String finalText = ""; //holds the final text to be returned after finished reading
            Scanner myReader = new Scanner(f1);
            while (myReader.hasNextLine()) {
                textHolder = myReader.nextLine();
                finalText += textHolder + "\n";
            }
            myReader.close();
            return finalText;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String readJsonplease(String filename)
    throws Exception
    {

        String output = "";
        output = new String(Files.readAllBytes(Paths.get(filename)));
        return output;

    }
}
