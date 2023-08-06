package util.JsonSerialization;

// Baseado em
// https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;

public class LocalTimeSerializer implements JsonSerializer <LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public JsonElement serialize(LocalTime localTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localTime));
    }
}
