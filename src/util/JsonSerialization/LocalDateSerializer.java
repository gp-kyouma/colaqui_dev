package util.JsonSerialization;

// Baseado em
// https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;

public class LocalDateSerializer implements JsonSerializer <LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}
