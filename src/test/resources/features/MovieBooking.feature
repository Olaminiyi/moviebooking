Feature: Movie booking system

  Scenario: Book a seat for a movie
    Given  a movie "Lizade" with 10 available seats
    When a customer book movie "Lizade" with 4 seats
    Then the total price should be 20.0
    And the total tax should be 0.4