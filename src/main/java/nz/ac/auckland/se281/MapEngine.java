package nz.ac.auckland.se281;

import java.util.List;

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
    String input = Utils.readStringInput();
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
