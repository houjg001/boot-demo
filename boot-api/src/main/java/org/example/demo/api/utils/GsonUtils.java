package org.example.demo.api.utils;

import com.google.gson.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonUtils {


    //序列化
    private final static JsonSerializer<LocalDate> dateSerializer = (localDate, type, jsonSerializationContext)
            -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_PATTERN)));
    private final static JsonSerializer<LocalDateTime> dateTimeSerializer = (localDateTime, type, jsonSerializationContext)
            -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(DateUtils.DATETIME_PATTERN)));
    //反序列化
    private final static JsonDeserializer<LocalDate> dateDeserializer = (jsonElement, type, jsonDeserializationContext)
            -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(DateUtils.DEFAULT_PATTERN));
    final static JsonDeserializer<LocalDateTime> dateTimeDeserializer = (jsonElement, type, jsonDeserializationContext)
            -> LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(DateUtils.DATETIME_PATTERN));

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, dateSerializer)
            .registerTypeAdapter(LocalDateTime.class, dateTimeSerializer)
            .registerTypeAdapter(LocalDate.class, dateDeserializer)
            .registerTypeAdapter(LocalDateTime.class, dateTimeDeserializer)
            .create();


    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, dateSerializer)
                .registerTypeAdapter(LocalDateTime.class, dateTimeSerializer)
                .registerTypeAdapter(LocalDate.class, dateDeserializer)
                .registerTypeAdapter(LocalDateTime.class, dateTimeDeserializer)
                .create();
    }
}
