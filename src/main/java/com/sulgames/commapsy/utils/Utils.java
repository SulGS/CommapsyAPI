package com.sulgames.commapsy.utils;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Utils {
	public static JsonObject stringToJson(String jsonToParse) 
	{
		JsonReader jReader = Json.createReader(new StringReader(jsonToParse));

		JsonObject jsonValues = jReader.readObject();

		jReader.close();
		
		return jsonValues;
	}
}
