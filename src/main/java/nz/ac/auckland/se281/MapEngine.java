package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Graph graph;

  public MapEngine() {
    this.graph = new Graph();
    loadMap();
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    for (int i = 0; i < countries.size(); i++) {
      // Read line and split country into parts
      String countryString = countries.get(i);
      String[] countryParts = countryString.split(",");

      // Use parts to create new Country object
      String countryName = countryParts[0];
      String continent = countryParts[1];
      int borderTax = Integer.parseInt(countryParts[2]);
      Country newCountry = new Country(countryName, continent, borderTax);

      // Read line and split adjacencies into parts
      String adjacentString = adjacencies.get(i);
      String[] adjacentParts = adjacentString.split(",");

      // Add the new Country with all it's adjacents
      for (int j = 0; j < adjacentParts.length; j++) {
        graph.addEdge(newCountry, adjacentParts[j]);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    try {
      // Ask user for input
      MessageCli.INSERT_COUNTRY.printMessage();
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.readStringInput());

      // Find matching country name
      Country country = graph.getCountryByName(input);

      if (country != null) {
        // Get and print country details if found
        String countryName = country.getCountryName();
        String continent = country.getContinent();
        String borderTax = String.valueOf(country.getBorderTax());
        MessageCli.COUNTRY_INFO.printMessage(countryName, continent, borderTax);
        return;
      } else {
        throw new InvalidCountryException(input);
      }
    } catch (InvalidCountryException e) {
      // If country not found, throw exception and print error message
      System.out.println(e.getMessage());

      // Repeat method
      showInfoCountry();
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country source = null;
    Country destination = null;
    List<Country> journey = new ArrayList<>();
    Set<String> continents = new LinkedHashSet<>();

    do {
      MessageCli.INSERT_SOURCE.printMessage();
      String sourceInput = Utils.capitalizeFirstLetterOfEachWord(Utils.readStringInput());
      source = graph.getCountryByName(sourceInput);
      if (source == null) {
        MessageCli.INVALID_COUNTRY.printMessage(sourceInput);
      }
    } while (source == null);

    do {
      MessageCli.INSERT_DESTINATION.printMessage();
      String destinationInput = Utils.capitalizeFirstLetterOfEachWord(Utils.readStringInput());
      destination = graph.getCountryByName(destinationInput);
      if (destination == null) {
        MessageCli.INVALID_COUNTRY.printMessage(destinationInput);
      }
    } while (destination == null);

    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    journey = graph.shortestPathBreadthFirstTraversal(source, destination);

    int countryCount = 0;
    int continentCount = 0;
    StringBuilder stringBuilderCountriesTraversed = new StringBuilder();
    StringBuilder stringBuilderContinentsTraversed = new StringBuilder();
    for (Country country : journey) {
      String countryName = country.getCountryName();
      continents.add(country.getContinent());
      countryCount++;

      stringBuilderCountriesTraversed.append(countryName);
      if (countryCount < journey.size()) {
        stringBuilderCountriesTraversed.append(", ");
      }
    }

    for (String continent : continents) {
      stringBuilderContinentsTraversed.append(continent);
      continentCount++;
      if (continentCount < continents.size()) {
        stringBuilderContinentsTraversed.append(", ");
      }
    }

    String countriesTraversed = stringBuilderCountriesTraversed.toString();
    String continentsTraversed = stringBuilderContinentsTraversed.toString();

    MessageCli.ROUTE_INFO.printMessage("[" + countriesTraversed + "]");
    MessageCli.CONTINENT_INFO.printMessage("[" + continentsTraversed + "]");
  }
}
