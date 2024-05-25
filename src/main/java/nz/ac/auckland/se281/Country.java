package nz.ac.auckland.se281;

public class Country {
  private String countryName;
  private String continent;
  private int borderTax;

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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
    result = prime * result + ((continent == null) ? 0 : continent.hashCode());
    result = prime * result + borderTax;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    if (countryName == null) {
      if (other.countryName != null) return false;
    } else if (!countryName.equals(other.countryName)) return false;
    if (continent == null) {
      if (other.continent != null) return false;
    } else if (!continent.equals(other.continent)) return false;
    if (borderTax != other.borderTax) return false;
    return true;
  }
}
