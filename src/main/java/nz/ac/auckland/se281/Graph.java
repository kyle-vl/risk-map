package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

  public void addEdge(Country node1, String node2) {
    addVertex(node1);
    adjacencyMap.get(node1).add(node2);
  }

  public Country getCountryByName(String name) {
    for (Country country : getAdjacencyMap().keySet()) {
      if (country.getCountryName().equals(name)) {
        return country;
      }
    }
    return null;
  }

  public List<Country> breadthFirstTraversal(Country source, Country destination) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    queue.add(source);
    visited.add(source);

    while (!queue.isEmpty()) {
      Country currentCountry = queue.poll();
      if (currentCountry.equals(destination)) {
        // If destination reached, stop and return the visited list
        return visited;
      }

      List<String> adjacents = getAdjacencyMap().get(currentCountry);
      for (String adjacentName : adjacents) {
        Country adjacent = getCountryByName(adjacentName);
        if (adjacent != null && !visited.contains(adjacent)) {
          visited.add(adjacent);
          queue.add(adjacent);
        }
      }
    }

    return visited;
  }
}
