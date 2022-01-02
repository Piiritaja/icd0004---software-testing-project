# icd0004 course project 2021/2022

## Project authors

- Kaspar Ustav

## Used stack

- Java
    - Junit

- Gradle

# Running the application

## Running tests

You can run tests using gradle.

````
./gradlew test
````

Tests are also automatically ran when pushing to main branch and on merge requests. You can manually restart the latest
Gitlab test pipeline to run the tests.

## Running locally

For running the application you first need to create a .jar file. Jar file is built with gradle.

In project root:

````
./gradlew jar
````

Jar file will then be located in `build/libs` directory.

With the .jar file created, you can now generate weather report(s) from .txt file
(passed as an argument when running the .jar file).

````
java -jar icd0004-project-1.0.jar <path-to-input-file>
````

Every line in .txt file represents exactly 1 city. Example .txt file for generating weather reports for Keila and
Tallinn

````text
Keila
Tallinn

````

Weather reports are generated to the same location where you are running the command from.