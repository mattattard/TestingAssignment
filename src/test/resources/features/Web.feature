Feature: Web functionality
         testing payment processing.

  Scenario:
    Given I am a user triying to process a payment
    When I submit correct details
    Then  I should be told that the payment was successful.
