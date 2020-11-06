Feature: Grofers Assignment

  Scenario: All the users of City Grofers complete more than half of their todo tasks.
    Given User has the todo tasks
    And User belongs to the city Grofers
    Then User Completed task percentage should be greater than 50%