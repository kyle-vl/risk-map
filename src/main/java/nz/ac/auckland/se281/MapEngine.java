package nz.ac.auckland.se281;

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
      // Repeat method if exception thrown
      showInfoCountry();
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country source = null;
    Country destination = null;

    // Invalid input handling
    do {
      MessageCli.INSERT_SOURCE.printMessage();
      String sourceInput = Utils.capitalizeFirstLetterOfEachWord(Utils.readStringInput());
      source = graph.getCountryByName(sourceInput);
    } while (source == null);

    do {
      MessageCli.INSERT_DESTINATION.printMessage();
      String destinationInput = Utils.capitalizeFirstLetterOfEachWord(Utils.readStringInput());
      destination = graph.getCountryByName(destinationInput);
    } while (destination == null);

    // Check if user has entered same source as destination
    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    List<Country> journey = graph.findShortestPathBreadthFirstTraversal(source, destination);

    int countryCount = 0;
    int continentCount = 0;
    int totalTax = 0;
    Set<String> continents = new LinkedHashSet<>();
    StringBuilder stringBuilderCountriesTraversed = new StringBuilder();
    StringBuilder stringBuilderContinentsTraversed = new StringBuilder();
    for (Country country : journey) {
      String countryName = country.getCountryName();
      continents.add(country.getContinent());
      countryCount++;

      stringBuilderCountriesTraversed.append(countryName);

      // Don't add comma after final country name
      if (countryCount < journey.size()) {
        stringBuilderCountriesTraversed.append(", ");
      }

      // Don't add border tax of source country
      if (countryCount > 1) {
        totalTax += country.getBorderTax();
      }
    }

    for (String continent : continents) {
      stringBuilderContinentsTraversed.append(continent);
      continentCount++;

      // Don't add comma after final continent name
      if (continentCount < continents.size()) {
        stringBuilderContinentsTraversed.append(", ");
      }
    }

    String countriesTraversed = stringBuilderCountriesTraversed.toString();
    String continentsTraversed = stringBuilderContinentsTraversed.toString();

    MessageCli.ROUTE_INFO.printMessage("[" + countriesTraversed + "]");
    MessageCli.CONTINENT_INFO.printMessage("[" + continentsTraversed + "]");
    MessageCli.TAX_INFO.printMessage(String.valueOf(totalTax));
  }
}
