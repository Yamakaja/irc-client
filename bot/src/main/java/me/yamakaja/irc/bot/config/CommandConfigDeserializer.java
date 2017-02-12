package me.yamakaja.irc.bot.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Yamakaja on 12.02.17.
 */
public class CommandConfigDeserializer implements JsonDeserializer<CommandConfig> {

    @Override
    public CommandConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String className = jsonElement.getAsJsonObject().get("clazz").getAsString();
        try {
            return jsonDeserializationContext.deserialize(jsonElement, Class.forName(className));
        } catch (ClassNotFoundException e) {
            System.err.println("Deserialization failed! " + className + " isn't a known class!");
            e.printStackTrace();
        }

        return null;
    }

}
