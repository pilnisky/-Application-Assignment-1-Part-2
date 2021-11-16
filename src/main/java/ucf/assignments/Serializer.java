/*
 *  UCF COP3330 Fall 2021 Assignment 1 part 2 Solution
 *  Copyright 2021 Palmer Ilnisky
 */


package ucf.assignments;

import com.google.gson.*;
import java.lang.reflect.Type;

public class Serializer implements JsonSerializer<Task> {
    public JsonElement serialize(final Task task, final Type type, final JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("description", new JsonPrimitive(task.getDescription()));
        result.add("completed", new JsonPrimitive(task.getCompleted()));
        if(task.getDueDate() != null)
            result.add("dueDate", new JsonPrimitive(task.getDueDate()));
        return result;
    }
}