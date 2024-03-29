package edu.cnm.deepdive.jata.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

/**
 * This class represents the game.
 */
@Entity
@Table(indexes = @Index(columnList = "game_id, boardSizeX, boardSizeY, playerCount"))
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({""})
public class Game {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "game_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(access = Access.READ_ONLY)
  private UUID key;

  @NonNull
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant created;

  @NonNull
  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonProperty(access = Access.READ_ONLY)
  private final List<UserGame> userGames = new LinkedList<>();

  @NonNull
  @JsonProperty(access = Access.READ_WRITE)
  private int boardSizeX;

  @NonNull
  @JsonProperty(access = Access.READ_WRITE)
  private int boardSizeY;

  @NonNull
  @JsonProperty(access = Access.READ_ONLY)
  private int playerCount;

  /**
   * Get game object's id
   * @return id Game's id
   */
  public Long getId() {
    return id;
  }

  /**
   * get game's secure UUID key
   * @return key UUID key
   */
  @NonNull
  public UUID getKey() {
    return key;
  }

  /**
   * Get the time and day the game was created.
   * @return created instant game was created.
   */
  @NonNull
  public Instant getCreated() {
    return created;
  }

  /**
   * get List of userGame.
   * @return userGame List of userGame.
   */
  @NonNull
  public List<UserGame> getUserGames() {
    return userGames;
  }

  /**
   *
   * Returns the size of the playing board's x-dimension
   * @return boardSizeX
   */
  public int getBoardSizeX() {
    return boardSizeX;
  }

  /**
   *
   * Annotates the size of the playing board's x-dimension
   * @param boardSizeX
   */
  public void setBoardSizeX(int boardSizeX) {
    this.boardSizeX = boardSizeX;
  }

  /**
   *
   * Returns the size of the playing board's y-dimension
   * @return boardSizeY
   */
  public int getBoardSizeY() {
    return boardSizeY;
  }

  /**
   *
   * Annotates the size of the playing board's x-dimension
   * @param boardSizeY
   */
  public void setBoardSizeY(int boardSizeY) {
    this.boardSizeY = boardSizeY;
  }

  /**
   * Get playerCount.
   * @return playerCount int playerCount.
   */
  public int getPlayerCount() {
    return playerCount;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }
}
