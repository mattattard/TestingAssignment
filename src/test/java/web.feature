
  Feature: Payment Processing Function:
    In order to process payment
    As someone who is wanting to pay
    I want to be able to process payment securely.

    Scenario:  All details entered correctly:
      Given     I am a user trying to process a payment
      When      I submit correct details
      Then      I should be told that the payment was successful


    Scenario Outline:  When a field is left empty :
      Given     I am a user trying to process a payment
      When      I submit a form with all data except <field>
      Then      I should be told that <field> is required

      Examples:
        | field     |
        | name      |
        | address   |
        | card      |
        | expiry    |
        | cvv       |
        | amount    |
