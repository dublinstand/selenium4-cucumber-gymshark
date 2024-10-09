Feature: Bag Operations

  Background:
    Given the user is on a product page

  Scenario: Remove a product from the Bag
    Given there are products in the bag
    When I remove a product
    Then the product is removed from the bag

  Scenario: Add quantity to the product
    Given there are products in the bag
    When I add quantity
    Then product quantity is increased

  Scenario: Remove quantity from the product
    Given there are products in the bag
    When I remove quantity
    Then product quantity is removed from the bag