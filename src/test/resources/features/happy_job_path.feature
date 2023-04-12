Feature: User creates new job

  Scenario: User creates new job
    Given user is logged in to the application
    When user creates new job
      | item name    | New Service       |
      | unit qty     | 2.00              |
      | unit price   | 100.00            |
      | private note | Private test note |
