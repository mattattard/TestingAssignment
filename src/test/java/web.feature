
  Feature: Payment Processing Function:
    In order to process payment
    As someone who is wanting to pay
    I want to be able to process payment securely.

    Scenario:  All details entered correctly:
      Given     I am a user trying to process a payment
      When      I submit correct details
      Then      I should be told that the payment was successful


    Scenario Outline:  Any Field is left empty
      Given     I am a user trying to process a payment
      When      I submit a form with all data except <fieldname>
      Then      I should be told that <fieldname> is required

      Examples:
      |fieldname|
      | name |
      | address |
      | card |
      | expiry |
      | cvv |
