@API @SOAP
Feature: SOAP API Calls for all basic operations

  @API_Post
  Scenario: Verification of SOAP API POST method
    Given user creates client instance for api call
    When user execute "POST" request
    Then user verifies response code as "200"
    And user verifies generated value "25"
