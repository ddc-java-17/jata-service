package edu.cnm.deepdive.jata.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.jata.view.ShotViews;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import java.util.Objects;

@Embeddable
@JsonView({ShotViews.Summary.class})
public class Location {
  public static final int MIN_X_COORD = 1;
  public static final int MIN_Y_COORD = 1;

  @Min(MIN_X_COORD)
  private int x;
  @Min(MIN_Y_COORD)
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
