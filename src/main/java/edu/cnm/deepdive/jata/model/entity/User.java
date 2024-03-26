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
import edu.cnm.deepdive.jata.view.UserView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

/**
 * This class represents the User.
 */
@Entity
@Table(name = "user_profile")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"key", "created", "modified", "displayName"})
@JsonView({UserView.External.class, UserGameView.Summary.class, ShotViews.Summary.class})
public class User {

  @Id
  @NonNull
  @GeneratedValue
  @Column(name = "user_profile_id", nullable = false, updatable = false)
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
  @JsonView(UserView.Internal.class)
  private Instant created;

  @NonNull
  @Column(nullable = false)
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  @JsonView(UserView.Internal.class)
  private Instant modified;

  @NonNull
  @Column(nullable = false, unique = true, length = 50)
  private String displayName;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 30)
  @JsonIgnore
  private String oauthKey;

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  @JsonIgnore
  private final List<UserGame> userGame = new LinkedList<>();

  /**
   * Gets user object's id.
   * @return id   User's id
   */
  @NonNull
  public Long getId() {
    return id;
  }

  /**
   * Gets User's secure UUID key
   * @return key  UUID key
   */
  @NonNull
  public UUID getKey() {
    return key;
  }

  /**
   * Gets the time and day that User was created.
   * @return created  Instant the User was created
   */
  @NonNull
  public Instant getCreated() {
    return created;
  }

  /**
   * Gets the last time the User modified their displayName.
   * @return modified  Instant User was last modified.
   */
  @NonNull
  public Instant getModified() {
    return modified;
  }

  /**
   * Gets User's current display name.
   * @return displayName  User's display name
   */
  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets User's current display name
   * @param displayName User's display name
   */
  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  /**
   * Gets User's OauthKey
   * @return OauthKey User's OauthKey
   */
  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * Sets User's OauthKey
   * @param oauthKey User's OauthKey
   */
  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  /**
   * Gets User's UserGame.
   * @return userGame UserGame object that the User points to
   */
  @NonNull
  public List<UserGame> getUserGame() {
    return userGame;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }


}
