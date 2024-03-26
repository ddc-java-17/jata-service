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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

/**
 * This class records every shot taken by every user in the game
 */
@Entity
@Table(
    indexes = {
        @Index(columnList = "shot_id, timestamp")
    }
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({""})
public class Shot {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "shot_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "from_user_game_id")
  @JsonProperty(access = Access.READ_ONLY)
  private UserGame fromUser;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "to_user_game_id")
  @JsonProperty(access = Access.READ_WRITE)
  private UserGame toUser;

  @NonNull
  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_WRITE)
  private Location location;

  @NonNull
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant timestamp;


  /**
   * Returns the unique ID of this shot
   *
   * @return
   */
  @NonNull
  public Long getId() {
    return id;
  }

  /**
   * Returns the user who fired the shot
   *
   * @return
   */
  @NonNull
  public UserGame getFromUser() {
    return fromUser;
  }

  /**
   * Annotates the user who fired the shot
   *
   * @param fromUser
   */
  public void setFromUser(@NonNull UserGame fromUser) {
    this.fromUser = fromUser;
  }

  /**
   * Returns the user who was fired upon
   *
   * @return
   */
  @NonNull
  public UserGame getToUser() {
    return toUser;
  }

  /**
   * Annotates the user who was fired upon
   *
   * @param toUser
   */
  public void setToUser(@NonNull UserGame toUser) {
    this.toUser = toUser;
  }

  @NonNull
  public Location getLocation() {
    return location;
  }

  public void setLocation(@NonNull Location location) {
    this.location = location;
  }

  /**
   * Returns the time the shot was fired
   *
   * @return
   */
  @NonNull
  public Instant getTimestamp() {
    return timestamp;
  }

  public boolean isHit() {
    return toUser
        .getLocations()
        .stream()
        .anyMatch((shipLocation) -> {
          Location loc = shipLocation.getLocation();
          return loc.getX() == location.getX()
              && loc.getY() == location.getY();
        });
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @SuppressWarnings("ConstantValue")
  @Override
  public boolean equals(Object obj) {
    boolean equals;
    if (this == obj) {
      equals = true;
    } else if (obj instanceof Shot other) {
      if (this.id != null && this.id.equals(other.id)) {
        equals = true;
      } else if (this.id == null && other.id == null) {
        equals = (this.toUser.equals(other.toUser)
            && this.location.equals(other.location));
      } else {
        equals = false;
      }
    } else {
      equals = false;
    }
    return equals;
  }

  //public boolean isHit() {

}


