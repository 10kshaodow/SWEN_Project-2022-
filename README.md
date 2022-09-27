# E-Store: **\_** _replace with your particular store type_ **\_**

# Modify this document to expand any and all sections that are applicable for a better understanding from your users/testers/collaborators (remove this comment and other instructions areas for your FINAL release)

An online E-store system built in Java 8=>11 and **\_ _replace with other platform requirements_ \_**

## Team

- Ryan Current
- Chris Hamm
- Michael Ververs
- Andrew Leon
- Colin Tondreau

## Prerequisites

- Java 8=>11 (Make sure to have correct JAVA_HOME setup in your environment)
- Maven
- _add any other tech stack requirements_

## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java` in the `estore-api` directory
3. Execute `ng serve --open` in the `estore-ui` directory
4. Your browser will open and you will be able to test the functionality

## Known bugs and disclaimers

v1.1 - No validation has been implemented, negative quantities and prices are allowed. 

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

\*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)

## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory

## How to setup/run/test program

1. Tester, first obtain the Acceptance Test plan
2. IP address of target machine running the app
3. Execute **\_\_\_\_**
4. ...
5. ...

## License

MIT License

See LICENSE for details.
