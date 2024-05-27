package nz.ac.auckland.se281;

/** This custom exception is thrown when the user enters an invalid country input. */
public class InvalidCountryException extends Exception {

  public InvalidCountryException(String input) {
    super("ERROR! This country was not found: " + input + ", try again!");
  }
}
