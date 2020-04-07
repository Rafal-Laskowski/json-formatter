package io.github.laskowski;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * This class relies on <b>com.google.code.gson</b> to create a pretty-formatted
 * json String.
 * 
 * @author rafal.laskowski
 *
 */
public class JSONFormatter {

	/**
	 * Processes the string, obtained from either String or File and formats it
	 * nulls will also be processed (serialized)
	 * 
	 * @return pretty-formatted String
	 */
	public static String format(String json) {
		JsonObject object;
		try {
			JsonParser parser = new JsonParser();
			object = parser.parse(json).getAsJsonObject();
		} catch (JsonSyntaxException | IllegalStateException e) {
			throw new JSONFormatterException("Provided JSON is malformed!");
		}

		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(object);
	}

	public static String format(File file) {
		JsonParser parser = new JsonParser();
		try {
			String json = parser.parse(new FileReader(file)).toString();
			return format(json);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			throw new JSONFormatterException(e);
		}
	}
}
