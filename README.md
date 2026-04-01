## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

HOW TO RUN CODE 
RQUIERMENTS
- Java Jdk 8 OR HIGHER
- Code editor
- JUnit 5 standalone JAR

SETUP

- Clone the repo and open it in IntelliJ
- Mark src as Sources Root (right-click -> Mark Directory As -> Sources Root)
- Mark src/security/test as Test Sources Root
- Add JUnit 5 JAR to the project (File -> Project Structure -> Libraries (OR LIB WHATEVER THE NAME IS) then click the plus symbol ->Java)

RUN DEMO

- Navigate to src/security/demo/Demo.java
- Right-click -> Run
- Output will print to console and access_log.txt will be created in the project root

RUN TESTS

- Right-click the src/security/test folder ->Run All Tests
All 59 tests should pass

Notes

Delete access_log.txt before running the demo if you want a clean log file
Tests use clearForTesting() so they don't affect each other
