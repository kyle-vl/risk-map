package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class a graph using a HashMap. The Country object itself is the key, while the values are a
 * LinkedList of adjacent countries.
 */
class Graph {
  private Map<Country, List<String>> adjacencyMap;

  public Graph() {
    this.adjacencyMap = new HashMap<>();
  }

  public Map<Country, List<String>> getAdjacencyMap() {
    return adjacencyMap;
  }

  public void addVertex(Country node) {
    adjacencyMap.putIfAbsent(node, new LinkedList<>());
  }

  /**
   * Creates an edge on the graph.
   *
   * @param node1 Creates a vertex for this node if it doesn't exist yet
   * @param node2 This node is added to the values of Node 1
   */
  public void addEdge(Country node1, String node2) {
    addVertex(node1);
    adjacencyMap.get(node1).add(node2);
  }

  /**
   * Finds country using a String of the countryName.
   *
   * @param name String countryName
   * @return returns Country object which matches String countryName
   */
  public Country getCountryByName(String name) {
    try {
      for (Country country : getAdjacencyMap().keySet()) {
        if (country.getCountryName().equals(name)) {
          return country;
        }
      }
      throw new InvalidCountryException(name);
    } catch (InvalidCountryException e) {
      // If country not found, throw exception and print error message
      System.out.println(e.getMessage());
      return null;
    }
  }

  /**
   * Uses BFS to find the shortest path between two countries.
   *
   * @param source The starting/first country in the path
   * @param destination The ending/last country in the path
   * @return Returns a list of countries containing the shortest path between source and destination
   */
  public List<Country> findShortestPathBreadthFirstTraversal(Country source, Country destination) {
    // Visited can be a HashSet as the order does not matter
    Set<Country> visited = new HashSet<>();
    Queue<Country> queue = new LinkedList<>();

    /* parentMap will keep track of where each node comes from,
    with the source as the head hence it's value being null. */
    Map<Country, Country> parentMap = new HashMap<>();
    parentMap.put(source, null);
    queue.add(source);
    visited.add(source);

    while (!queue.isEmpty()) {
      Country currentCountry = queue.poll();

      // Get all adjacent countries from string countryName
      List<String> adjacents = getAdjacencyMap().get(currentCountry);
      for (String adjacentName : adjacents) {
        Country adjacent = getCountryByName(adjacentName);

        if (adjacent != null && !visited.contains(adjacent)) {
          /* Add adjacent to visited and queue lists if it hasn't been visited yet.
          Record the adjacent's parent on the parentMap. */
          visited.add(adjacent);
          queue.add(adjacent);
          parentMap.put(adjacent, currentCountry);

          // If the adjacent is the destination, reconstruct path.
          if (adjacent.equals(destination)) {
            List<Country> path = new ArrayList<>();
            Country node = adjacent;
            while (node != null) {
              path.add(node);
              node = parentMap.get(node);
            }
            /* Path is in backwards order as it traverses from destination to source,
            so it must be reversed before being returned. */
            Collections.reverse(path);
            return path;
          }
        }
      }
    }
    // This method should (theoretically) never return null as each node is connected on the graph.
    return null;
  }
}
