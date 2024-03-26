package edu.cnm.deepdive.jata.model.entity;

import edu.cnm.deepdive.jata.model.Location;
import java.util.List;

public class Ship {

  private int number;

  private List<Location> locations;

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }
}
