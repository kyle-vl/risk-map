package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
}
