package com.remitly;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonVerifier {

    //path can be changed but the analyze function will work fine with any path
    private String pathToTargetElement = "PolicyDocument;Statement;Resource";

    public JsonVerifier() {
    }

    public JsonVerifier(String pathToTargetElement) {
        this.pathToTargetElement = pathToTargetElement;
    }

    //method to verify the input JSON used in test cases
    public boolean verifyInput(String jsonContent) {
        JSONObject data = new JSONObject(jsonContent);
        return analyze(data, pathToTargetElement);
    }

    //method to verify the input JSON from a file as stated int the excercise
    public boolean verifyInputFromFile(String filePath) throws FileNotFoundException {
        try {
            // Read JSON file
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject data = new JSONObject(content);
            return analyze(data, pathToTargetElement);

        } catch (IOException e) {
            throw new FileNotFoundException("JSON File not found");
        }
    }

    private boolean analyze(JSONObject data, String path) {
        String[] pathElements = path.split(";", 2);
        String currentElement = pathElements[0];
        JSONObject currentData;
        if (!data.has(currentElement)) {
            throw new IllegalArgumentException("Invalid JSON format");
        }
        if (pathElements.length > 1) {
            if (data.get(currentElement) instanceof JSONArray) {
                JSONArray statements = data.getJSONArray(currentElement);
                for (int i = 0; i < statements.length(); i++) {
                    currentData = statements.getJSONObject(i);
                    boolean result = analyze(currentData, pathElements[1]);
                    if (!result) {
                        return false;
                    }
                }
                return true;
            } else {
                currentData = data.getJSONObject(currentElement);
                return analyze(currentData, pathElements[1]);
            }

        } else {
            if (data.has(currentElement) && data.getString(currentElement).equals("*")) {
                return false;
            } else {
                return true;
            }
        }
    }
}
