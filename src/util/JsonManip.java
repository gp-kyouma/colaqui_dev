package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import util.JsonSerialization.*;

public class JsonManip {
    public static <T> ArrayList<T> readArrayListFromJson(String filename, Class<T> cls)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
        Gson gson = gsonBuilder.create();

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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

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
