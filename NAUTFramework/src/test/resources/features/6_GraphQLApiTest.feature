@API @Graph
Feature: GraphQL API Calls for all basic operations

  @API_Post
  Scenario: Verification of GraphQL POST method
    Given user creates client instance for api call
    When user execute "POST" request
    Then user verifies response code as "200"
    #And user verifies generated id

  @API_Get
  Scenario: Verification of GraphQL get method using POST API call
    Given user creates client instance for api call
    When user execute "GET" request
    Then user verifies response code as "200"
    #And user verifies generated id

  @API_Put
  Scenario: Verification of GraphQL update method using POST API call
    Given user creates client instance for api call
    When user execute "PUT" request
    Then user verifies response code as "200"

  @API_Delete
  Scenario: Verification of GraphQL DELETE method using POST API call
    Given user creates client instance for api call
    When user execute "DELETE" request
    Then user verifies response code as "200"
    And user resets client parameters
