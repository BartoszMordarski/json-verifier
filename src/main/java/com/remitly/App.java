package com.remitly;

import java.io.FileNotFoundException;

public class App
{
    public static void main(String[] args) throws FileNotFoundException {
        JsonVerifier verifier = new JsonVerifier();
        if(args.length == 0){
            System.out.println("Please provide the path to the JSON file");
            return;
        }
        boolean result = verifier.verifyInputFromFile(args[0]);
        System.out.println("Is input JSON file valid?: " + result);
    }
}
