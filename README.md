# json-verifier

The `json-verifier` project is a tool written in Java that verifies if the input JSON data conforms to a specific format. The input data format is defined as AWS::IAM::Role Policy. The tool returns a logical `false` if the "Resource" field in the input JSON data contains a single asterisk, and `true` in any other case. If the input file doesn't have the expected json format, the tool will throw an exception. The default path to the target json element is defined as variable `pathToTargetElement` in the `JsonVerifier` class. You can also use this tool in other projects by importing the `JsonVerifier` class and using constructor with the desired `pathToTargetElement` as a parameter. It allows analyzing json documents in other formats. The format of the path is as follows:
```java
String pathToTargetElement = "PolicyDocument;Statement;Resource";
```
Key nodes of the path are separated by a semicolon. The path is case-sensitive. The tool uses the Jackson library to parse JSON data.
# Example Input JSON File

Below is an example input JSON file that conforms to the AWS::IAM::Role Policy format:

```json
{
  "PolicyName": "root",
  "PolicyDocument": {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Sid": "IamListAccess",
        "Effect": "Allow",
        "Action": [
          "iam:ListRoles",
          "iam:ListUsers"
        ],
        "Resource": "*"
      }
    ]
  }
}
```
JsonVerifier class contains two methods: one taking a path to the json file as an argument and the other one taking a json as a String as an argument. Both methods return a boolean value. The main logic is implemented in the analyze method. The method takes a json object and a path to the target element as arguments. The method returns a boolean value. The method is recursive and checks if the target element is present in the json object. If the target element is present and its value is "*", the method returns false. Otherwise, it returns true. The method throws an exception if the json object doesn't have the expected format. The method is implemented as follows
``` java
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
```
    

# Running the Project from command line
To run the JsonVerifier from cmd, follow these steps:
1. **Clone the Repository**
- Clone the project repository to your local environment using the git clone command.
2. **Build the Project**
- Navigate to the project directory using the cd json-verifier command.  
Build the project using the Maven tool by executing the mvn clean install command. As a result, the fat-jar: json-verifier-1.0-SNAPSHOT-jar-with-dependencies.jar file will be created in the target directory.
3. **Run the Project**
- Run the main class of the project, passing the path to the JSON file as an argument. For example:
```console
java -jar ./json-verifier-1.0-SNAPSHOT-jar-with-dependencies.jar C:/Users/Admin/IdeaProjects/json_verifier/src/test/resources/correct_input.json
```
- If you don't provide the path to the JSON file, the tool will prompt you to do so:
```console
C:\Users\Admin\IdeaProjects\json_verifier\target>java -jar ./json-verifier-1.0-SNAPSHOT-jar-with-dependencies.jar
Please provide the path to the JSON file
```
# Running the Project from IDE
To run the JsonVerifier from an IDE, follow these steps:
1. **Clone the Repository**
2. **Open the Project in an IDE**
3. **Build the Project**
4. **Provide the Path to the JSON File**
5. **Run the App class and provide path to json file as input argument**
