package nz.ac.auckland.se281;

public class InvalidCountryException extends Exception {

  public InvalidCountryException(String input) {
    super("ERROR! This country was not found: " + input + ", try again!");
  }
}
