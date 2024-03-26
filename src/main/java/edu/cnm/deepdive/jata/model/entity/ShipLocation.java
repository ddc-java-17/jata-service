package edu.cnm.deepdive.jata.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.cnm.deepdive.jata.model.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

/**
 * This class contains a single ship of some type.  Each row represents a single point on the ship.
 * Ships size is determined by the number of rows in the table
 */
@Entity
@Table(indexes = @Index(columnList = "ship_id, shipNumber"))
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({""})
public class ShipLocation {

  public static final int MIN_SHIP_NUMBER = 1;
  public static final int MIN_X_COORD = 1;
  public static final int MIN_Y_COORD = 1;
  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "ship_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_game_id")
  @JsonProperty(access = Access.READ_ONLY)
  @JsonIgnore
  private UserGame userGame;

  @Column(nullable = false, updatable = true)
  @JsonProperty(access = Access.READ_WRITE)
  @Min(MIN_SHIP_NUMBER)
  private int shipNumber;

  @Column(nullable = false, updatable = true)
  @JsonProperty(access = Access.READ_WRITE)
  private Location location;

  /**
   * Returns the ID of the ship
   *
   * @return
   */
  @NonNull
  public Long getId() {
    return id;
  }

  /**
   * Returns the userGame of a particular ship
   *
   * @return
   */
  @NonNull
  public UserGame getUserGame() {
    return userGame;
  }

  /**
   * Annotates this ship with its associated user via UserGame
   *
   * @param userGame
   */
  public void setUserGame(@NonNull UserGame userGame) {
    this.userGame = userGame;
  }

  /**
   * Returns the ship number (identifier) for a particular ship belonging to a userGame
   *
   * @return
   */
  public int getShipNumber() {
    return shipNumber;
  }

  /**
   * Annotates the ship number (identifier) for a particular ship belonging to a userGame
   *
   * @param shipNumber
   */
  public void setShipNumber(int shipNumber) {
    this.shipNumber = shipNumber;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
