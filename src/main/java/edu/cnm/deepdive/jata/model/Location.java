package edu.cnm.deepdive.jata.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import java.util.Objects;

@Embeddable
public class Location {

  @Min(1)
  private int x;
  @Min(1)
  private int y;

  public Location() {
  }

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    boolean equals;
    if (this == obj) {
      equals = true;
    } else if (obj instanceof Location other) {
      equals = (this.x == other.x && this.y == other.y);
    } else {
      equals = false;
    }
    return equals;
  }
}
