package edu.cnm.deepdive.jata.model.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.cnm.deepdive.jata.model.ShotStatusId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import net.jcip.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@SuppressWarnings({"JpaDataSourceORMInspection", "SqlDialectInspection", "SqlResolve"})
@Entity
@Immutable
@Subselect("SELECT * FROM shot_status")
public class ShotStatus {

  public ShotStatus() {
  }

  public ShotStatus(ShotStatusId id, UserGame player, boolean hit) {
    this.id = id;
    this.player = player;
    this.hit = hit;
  }

  @Id
  @JsonUnwrapped
  private ShotStatusId id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "player_id")
  public UserGame player;

  public boolean hit;

  public ShotStatusId getId() {
    return id;
  }

  public boolean isHit() {
    return hit;
  }

  public UserGame getPlayer() {
    return player;
  }

}
