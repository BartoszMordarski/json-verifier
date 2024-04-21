package com.remitly;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class JsonVerifierTest {

private JsonVerifier verifier;

    @Before
    public void setUp() throws Exception {
        verifier = new JsonVerifier();
    }

    private String getJsonData(String path) throws URISyntaxException, IOException {
        URL resourceUrl = getClass().getClassLoader().getResource(path);
        URI resourceUri = resourceUrl.toURI();
        return new String(Files.readAllBytes(Paths.get(resourceUri)));
    }

    // Test case for verifyInputJson method when the input JSON is correct
    @Test
    public void verifyInputJson_correctInput() throws Exception{
        String jsonData = getJsonData("correct_input.json");
        assertTrue(verifier.verifyInput(jsonData));
    }

    // Test case for verifyInputJson method when the input JSON has 2 correct statements
    @Test
    public void verifyInputJson_correct2Inputs() throws Exception{
        String jsonData = getJsonData("correct_input_with_2_statements.json");
        assertTrue(verifier.verifyInput(jsonData));
    }

    // Test case for verifyInputJson method when the input JSON has an asterisk in first Statement
    @Test
    public void verifyInputJson_ContainsAsterisk1() throws Exception{
        String jsonData = getJsonData("asterisk_at_first_statement.json");
        assertFalse(verifier.verifyInput(jsonData));
    }

    // Test case for verifyInputJson method when the input JSON has an asterisk in second Statement
    @Test
    public void verifyInputJson_ContainsAsterisk2() throws Exception{
        String jsonData = getJsonData("asterisk_at_second_statement.json");
        assertFalse(verifier.verifyInput(jsonData));
    }

    // Test case for verifyInputJson method when the input JSON has no resource
    @Test(expected = IllegalArgumentException.class)
    public void verifyInputJson_NoResource() throws Exception{
        String jsonData = getJsonData("missing_resource.json");
        verifier.verifyInput(jsonData);
    }

   // Test case for verifyInputJson method when the input JSON has no statement
    @Test(expected = IllegalArgumentException.class)
    public void verifyInputJson_NoStatement() throws Exception{
        String jsonData = getJsonData("missing_statement.json");
        verifier.verifyInput(jsonData);
    }

    // Test case for verifyInputJson method when the input JSON has no policy document
    @Test(expected = IllegalArgumentException.class)
    public void verifyInputJson_NoPolicyDocument() throws Exception{
        String jsonData = getJsonData("missing_policy_document.json");
        verifier.verifyInput(jsonData);
    }

}
