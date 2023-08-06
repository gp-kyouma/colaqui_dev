package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonManip {
    public static <T> ArrayList<T> readArrayListFromJson(String filename, Class<T> cls)
    {
        Gson gson = new Gson();

        String output = "";
        try {
            output = TextReader.readFileToString(filename);
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        
        return gson.fromJson(output,TypeToken.getParameterized(ArrayList.class, cls).getType());
    }

    public static void saveArrayListToJson(String filename, ArrayList<?> list)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);
        try
        {
            FileWriter fw = new FileWriter(filename);
            fw.write(json);
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
            ioe.printStackTrace();
            System.exit(1);
        }
    }
}
