# A Hello World Test Scenario
Feature: a message can be retrieved
  Scenario: client makes a call to GET /service/hello?name=developer
    When the client calls /service/hello?name=developer
    Then the client receives status code of 200
    And the client receives a welcome message of Hello developer!