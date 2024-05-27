package nz.ac.auckland.se281;

/**
 * As each country contains a name, continent, and border tax, this class will be the key for the
 * map.
 */
public class Country {
  private String countryName;
  private String continent;
  private int borderTax;

  /**
   * Country constructor.
   *
   * @param countryName Name of country
   * @param continent Contient that country is located
   * @param borderTax Tax amount required to enter country
   */
  public Country(String countryName, String continent, int borderTax) {
    this.countryName = countryName;
    this.continent = continent;
    this.borderTax = borderTax;
  }

  public String getCountryName() {
    return countryName;
  }

  public String getContinent() {
    return continent;
  }

  public int getBorderTax() {
    return borderTax;
  }

  @Override
  public int hashCode() {
    /* hashCode method must be overridden to ensure Country
    objects that are not equal do not have matching hashcodes. */
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
    result = prime * result + ((continent == null) ? 0 : continent.hashCode());
    result = prime * result + borderTax;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    // Objects must be equal if they have the same address
    if (this == obj) {
      return true;
    }

    // Objects cannot be equal if the object is null
    if (obj == null) {
      return false;
    }

    // Objects cannot be equal if the object belongs to another class
    if (getClass() != obj.getClass()) {
      return false;
    }

    // Every variable must be identical for objects to be equal
    Country other = (Country) obj;
    if (countryName == null) {
      if (other.countryName != null) {
        return false;
      }
    } else if (!countryName.equals(other.countryName)) {
      return false;
    }
    if (continent == null) {
      if (other.continent != null) {
        return false;
      }
    } else if (!continent.equals(other.continent)) {
      return false;
    }
    if (borderTax != other.borderTax) {
      return false;
    }
    return true;
  }
}
