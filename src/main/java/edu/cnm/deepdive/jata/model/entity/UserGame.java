package edu.cnm.deepdive.jata.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.jata.view.ShotViews;
import edu.cnm.deepdive.jata.view.UserGameView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.NonNull;

/**
 * This class is the central hub of communication. All information the user needs comes through
 * this class.
 */

@Entity
@Table(name = "user_game", indexes = {
    @Index(columnList = "user_game_id, user_id"),
})
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"user_game_id"})
@JsonView({UserGameView.Summary.class, ShotViews.Summary.class})
public class UserGame {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "user_game_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(access = Access.READ_WRITE)
  private UUID key;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private User user;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", nullable = false, updatable = false)
  @JsonIgnore
  private Game game;

  @OneToMany(mappedBy = "userGame", fetch = FetchType.EAGER,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<ShipLocation> locations = new LinkedList<>();

  @OneToMany(mappedBy = "fromUser", fetch = FetchType.EAGER,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<Shot> fromShots = new LinkedList<>();

  @OneToMany(mappedBy = "toUser", fetch = FetchType.EAGER,
      cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonProperty(access = Access.READ_ONLY)
  @JsonView(UserGameView.Detailed.class)
  private final List<Shot> toShots = new LinkedList<>();

  @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
  @OrderBy
  private final List<ShotStatus> shotStatuses = new LinkedList<>();

  /**
   * Gets this UserGame's identifying number
   * @return id UserGame identification
   */
  @NonNull
  public Long getId() {
    return id;
  }


  @NonNull
  public UUID getKey() {
    return key;
  }

  public void setKey(@NonNull UUID key) {
    this.key = key;
  }

  /**
   * Gets the user object associated with this UserGame
   * @return user User object
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * Sets the user object associated with this UserGame
   * @param user  User object.
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Gets the game object associated with this UserGame
   * @return game Game object
   */
  @NonNull
  public Game getGame() {
    return game;
  }

  /**
   * Sets the game associated to this UserGame
   * @param game  Game object
   */
  public void setGame(@NonNull Game game) {
    this.game = game;
  }

  public List<ShipLocation> getLocations() {
    return locations;
  }

  public List<Shot> getFromShots() {
    return fromShots;
  }

  public List<Shot> getToShots() {
    return toShots;
  }

  public List<ShotStatus> getShotStatuses() {
    return shotStatuses;
  }

  public int getShipLocationCount() {
    return locations.size();
  }

  public int getToShotHits() {
    return (int) toShots
        .stream()
        .filter(Shot::isHit)
        .distinct()
        .count();
  }

  public boolean isFleetSunk() {
    return getToShotHits() >= getShipLocationCount();
  }

  @JsonProperty("locations")
  public List<ShipLocation> getExposedLocations() {
    return isFleetSunk() ? locations : null;
  }

  @JsonProperty("myLocations")
  @JsonView(UserGameView.Detailed.class)
  public List<ShipLocation> getMyLocations() {
    return locations;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }
}
