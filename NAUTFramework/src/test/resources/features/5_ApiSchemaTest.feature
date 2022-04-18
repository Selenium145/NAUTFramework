@API
Feature: Test API Calls for Schema validation

  @API_PostSchema
  Scenario: Verification of POST method with invalid schema   
    Given user creates client instance for api call
    When user execute "POST" request
    Then user verifies response code as "400"
    #And user resets client parameters
    
 
 