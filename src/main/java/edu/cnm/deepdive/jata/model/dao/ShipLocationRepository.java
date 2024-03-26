package edu.cnm.deepdive.jata.model.dao;

import edu.cnm.deepdive.jata.model.entity.ShipLocation;
import edu.cnm.deepdive.jata.model.entity.User;
import edu.cnm.deepdive.jata.model.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShipLocationRepository extends JpaRepository<ShipLocation, Long> {


  /**
   * This Query is used to determine if a user already has ships in the ShipLocation table.
   * This returns a count of the total locations in the ShipLocation table associated
   * with a particular userGame.
   * @param userGame
   * @return
   */
  @Query("SELECT count(sl) AS count FROM ShipLocation AS sl WHERE sl.userGame = :userGame")
  ShipsCount findShipLocationByUserGame(UserGame userGame);

  /**
   * This is used to isolate a particular ship to determine the validity of its make-up
   * @param number
   * @param userGame
   * @return
   */
  @Query("SELECT sl.location.x AS x, sl.location.y AS y FROM ShipLocation AS sl WHERE sl.shipNumber = :number AND sl.userGame = :userGame")
  ShipValid findShipLocationByShipNumberAndUserGame(int number, UserGame userGame);
}
