package util.JsonSerialization;

// Baseado em
// https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalTimeDeserializer implements JsonDeserializer <LocalTime> {
    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
        return LocalTime.parse(json.getAsString(),
            DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.ENGLISH));
    }
}
