package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  public MapEngine() {
    loadMap();
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    String input = Utils.readStringInput();
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
